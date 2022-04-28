<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />

<c:set var="commFavIdx" value="${ForwardConst.CMD_FAVORITE_INDEX.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>いいねした人一覧</h2>
        <table id="favorite_list">
            <tr>
                <th class="favorite_employee_id">いいねした人</th>
                <th class="favorite_created_at">いいねした日時</th>
            </tr>
            <c:forEach var="favorite" items="${favorites}" varStatus="status">
                <fmt:parseDate value="${favorite.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                <tr class="row${status.count % 2}">
                    <td class="favorite_employee_id"><c:out value="${favorite.employee.name}" /></td>
                    <td class="favorite_created_at"><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </c:forEach>
        </table>

        <div id="pagination">
           (全 ${favorite_reports_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((favorite_reports_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actRep}&command=${commFavIdx}&id=${id}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <p>
            <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
        </p>

    </c:param>
</c:import>