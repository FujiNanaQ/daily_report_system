package services;

import java.time.LocalDateTime;

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
}
