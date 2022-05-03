package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowConverter;
import actions.views.FollowView;
import constants.JpaConst;
import models.Follow;

/**
 * フォローテーブルの操作に関わる処理を行うクラス
 *
 */
public class FollowService extends ServiceBase {

    /**
     * ログイン中の従業員id、指定された従業員idを条件に取得したデータをFollowViewのインスタンスで返却する
     * @param followerEmployee フォローする従業員（ログイン中の従業員）
     * @param followedEmployee フォローされた従業員
     * @return 取得データのインスタンス、取得できない場合はnull
     */
    public FollowView findOne(EmployeeView followerEmployee, EmployeeView followedEmployee) {
        Follow fo = null;
        try {
            fo = em.createNamedQuery(JpaConst.Q_FOL_GET_BY_FOLLOWER_AND_FOLLOWED, Follow.class)
                    .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(followerEmployee))
                    .setParameter(JpaConst.JPQL_PARM_FOLLOWED, EmployeeConverter.toModel(followedEmployee))
                    .getSingleResult();
        } catch (NoResultException ex) {
        }

        return FollowConverter.toView(fo);
    }

    /**
     * 指定した従業員がフォローしているデータを、指定されたページ数の一覧画面に表示する分取得しFollowViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowView> getMinePerPage(EmployeeView followerEmployee, int page) {
        List<Follow> follows = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL_MINE, Follow.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(followerEmployee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowConverter.toViewList(follows);
    }

    /**
     * 指定した従業員がフォローしているデータの件数を取得し、返却する
     * @param followerEmployee
     * @return 日報データの件数
     */
    public long countAllMine(EmployeeView followerEmployee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_FOL_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_FOLLOWER, EmployeeConverter.toModel(followerEmployee))
                .getSingleResult();

        return count;
    }

    /**
     * フォローデータを1件作成し、登録する
     * @param fov フォローデータ
     */
    public void create(FollowView fov) {
        LocalDateTime ldt = LocalDateTime.now();
        fov.setCreatedAt(ldt);
        fov.setUpdatedAt(ldt);
        createInternal(fov);
    }

    /**
     * フォローデータを1件登録する
     */
    private void createInternal(FollowView fov) {
        em.getTransaction().begin();
        em.persist(FollowConverter.toModel(fov));
        em.getTransaction().commit();
    }

    /**
     * 追記
     */
    public void destroy(FollowView fov) {
        destroyInternal(FollowConverter.toModel(fov));
    }

    /**
     * フォローデータを1件削除する
     */
    private void destroyInternal(Follow fo) {
        em.getTransaction().begin();
        fo = em.merge(fo);
        em.remove(fo);  //データ削除
        em.getTransaction().commit();
    }

}
