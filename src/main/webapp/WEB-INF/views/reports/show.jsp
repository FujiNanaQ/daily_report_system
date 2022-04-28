<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commFav" value="${ForwardConst.CMD_FAVORITE_COUNT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>日報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${report.employee.name}" /></td>
                </tr>
                 <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                 <tr>
                    <th>内容</th>
                    <td><pre><c:out value="${report.content}" /></pre></td>
                </tr>
                 <tr>
                    <th>日報最終更新日時</th>
                    <fmt:parseDate value="${report.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>いいね数</th>
                    <td><c:out value="${report.favoriteCount}" /></td>
                </tr>
            </tbody>
        </table>

        <c:choose>
            <c:when test="${sessionScope.login_employee.id == report.employee.id}">
                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この日報を編集する</a>
                </p>
            </c:when>
            <c:otherwise>
                <c:if test="${favorite_find_one == null}">
                    <p>
                        <a href="<c:url value='?action=${actRep}&command=${commFav}&id=${report.id}' />">この日報にいいねする</a>
                    </p>
                </c:if>
            </c:otherwise>
        </c:choose>

                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
                </p>

    </c:param>
</c:import>