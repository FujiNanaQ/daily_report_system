package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_FAV)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_FAV_GET_ALL_MINE,
            query = JpaConst.Q_FAV_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_FAV_COUNT_ALL_MINE,
            query = JpaConst.Q_FAV_COUNT_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_FAV_GET_BY_EMPLOYEE_AND_REPORT,
            query = JpaConst.Q_FAV_GET_BY_EMPLOYEE_AND_REPORT_DEF)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Favorite {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FAV_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * いいねをした従業員のid
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FAV_COL_EMPLOYEE_ID, nullable = false)
    private Employee employee;


    /**
     * いいねをされた日報のid
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FAV_COL_REPORT_ID, nullable = false)
    private Report report;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.FAV_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.FAV_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}
