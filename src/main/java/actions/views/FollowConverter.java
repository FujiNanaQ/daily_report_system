package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Follow;

/**
 * DTOモデルとViewモデルの変換を行うクラス
 *
 */
public class FollowConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param fov FollowViewのインスタンス
     * @return Followのインスタンス
     */
    public static Follow toModel(FollowView fov) {
        return new Follow(
                fov.getId(),
                EmployeeConverter.toModel(fov.getFollowerEmployee()),
                EmployeeConverter.toModel(fov.getFollowedEmployee()),
                fov.getCreatedAt(),
                fov.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param fo Followのインスタンス
     * @return FollowViewのインスタンス
     */
    public static FollowView toView(Follow fo) {

        if(fo ==null) {
            return null;
        }

        return new FollowView(
                fo.getId(),
                EmployeeConverter.toView(fo.getFollowerEmployee()),
                EmployeeConverter.toView(fo.getFollowedEmployee()),
                fo.getCreatedAt(),
                fo.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<FollowView> toViewList(List<Follow> list){
        List<FollowView> evs = new ArrayList<>();

        for (Follow fo : list) {
            evs.add(toView(fo));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param fo DTOモデル（コピー先）
     * @return fov Viewモデル（コピー元）
     */
    public static void copyViewToModel(Follow fo, FollowView fov) {
        fo.setId(fov.getId());
        fo.setFollowerEmployee(EmployeeConverter.toModel(fov.getFollowerEmployee()));
        fo.setFollowedEmployee(EmployeeConverter.toModel(fov.getFollowedEmployee()));
        fo.setCreatedAt(fov.getCreatedAt());
        fo.setUpdatedAt(fov.getUpdatedAt());
    }
}
