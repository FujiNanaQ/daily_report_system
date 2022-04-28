package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 画面の入力値・出力値を扱うviewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteView {

    /**
     * id
     */
    private Integer id;

    /**
     * いいねした授業員のid
     */
    private EmployeeView employee;

    /**
     * いいねされた日報のid
     */
    private ReportView report;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

}
