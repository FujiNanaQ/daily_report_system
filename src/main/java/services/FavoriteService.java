package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FavoriteConverter;
import actions.views.FavoriteView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Favorite;

/**
 * いいねテーブルの操作に関わる処理を行うクラス
 *
 */
public class FavoriteService extends ServiceBase{

    /**
     * 指定した、日報がいいねされたデータを、指定されたページ数の一覧画面に表示する分取得しFavoriteViewのリストで返却する
     * @param report 日報
     * @param page ページ数
     * @return いいね一覧画面に表示するデータのリスト
     */
    public List<FavoriteView> getMinePerPage(ReportView report, int page) {
        List<Favorite> favorites = em.createNamedQuery(JpaConst.Q_FAV_GET_ALL_MINE, Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FavoriteConverter.toViewList(favorites);
    }

    /**
     * 指定した日報がいいねされたデータの件数を取得し、返却する
     * @param report
     * @return いいねデータの件数
     */
    public long countAllMine(ReportView report) {
        long count = (long) em.createNamedQuery(JpaConst.Q_FAV_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return count;
    }

    /**
     * 従業員id、指定された日報のidを条件に取得したデータをFavoriteViewのインスタンスで返却する
     * @param employeeId 従業員id
     * @param reportId 日報id
     * @return 取得データのインスタンス、取得できない場合はnull
     */
    public FavoriteView findOne(EmployeeView employee, ReportView report) {
        Favorite f = null;
        try {
            f = em.createNamedQuery(JpaConst.Q_FAV_GET_BY_EMPLOYEE_AND_REPORT, Favorite.class)
                    .setParameter(JpaConst.JPQL_PARM_FAV_EMP_ID, EmployeeConverter.toModel(employee))
                    .setParameter(JpaConst.JPQL_PARM_FAV_REP_ID, ReportConverter.toModel(report))
                    .getSingleResult();
        } catch (NoResultException ex) {
        }

        return FavoriteConverter.toView(f);
    }


    /**
     * いいねデータを1件作成し、登録する
     * @param fv いいねデータ
     */
    public void create(FavoriteView fv) {
        LocalDateTime ldt = LocalDateTime.now();
        fv.setCreatedAt(ldt);
        fv.setUpdatedAt(ldt);
        createInternal(fv);
    }

    /**
     * いいねデータを1件登録する
     */
    private void createInternal(FavoriteView fv) {
        em.getTransaction().begin();
        em.persist(FavoriteConverter.toModel(fv));
        em.getTransaction().commit();

    }
}
