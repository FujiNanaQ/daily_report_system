package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EmployeeService;
import services.FollowService;
import services.ReportService;


/**
 * タイムラインに関する処理を行うActionクラス
 *
 */
public class FollowAction extends ActionBase {

    private FollowService service;

    private EmployeeService employeeService;
    private ReportService reportService;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowService();

        employeeService = new EmployeeService();
        reportService = new ReportService();

        //メソッドを実行
        invoke();

        service.close();
        employeeService.close();
        reportService.close();
    }

    /**
     * 従業員をフォローする
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //idを条件に、フォローする従業員データを取得
        EmployeeView followedEmployee = employeeService.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView followerEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (followedEmployee == null || followerEmployee.getId() == followedEmployee.getId()) {
            //該当の従業員データが存在しない、または、
            //ログインしている従業員がフォローする従業員と同じである場合は、エラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            //フォローデータのインスタンスを作成する
            FollowView fov = new FollowView(
                    null,
                    followerEmployee,
                    followedEmployee,
                    null,
                    null);

            //フォロー情報登録
            service.create(fov);

            //セッションにフォロー完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_FOLLOWED.getMessage());

            //タイムラインページへリダイレクト
            redirect(ForwardConst.ACT_FOL, ForwardConst.CMD_TIMELINE);
        }
    }

    /**
     * タイムラインを表示する
     * @throws ServletException
     * @throws IOException
     */
    public void timeline() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView followerEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員がフォローしている従業員の日報を、更新日時の降順で、指定されたページの一覧画面に表示する分取得する
        int page = getPage();
        List<ReportView> reports = reportService.getFollowedPerPage(followerEmployee, page);

        //ログイン中の従業員がフォローしている従業員の日報の件数を取得する
        long followedReportCount = reportService.countAllFollowed(followerEmployee);

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.FOL_COUNT, followedReportCount); //日報の件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数


        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        forward(ForwardConst.FW_FOL_TIMELINE);
    }

}