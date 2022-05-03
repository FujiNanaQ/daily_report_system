<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actFol" value="${ForwardConst.ACT_FOL.getValue()}" />

<c:set var="commDst" value="${ForwardConst.CMD_DESTROY.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>フォローしている人一覧</h2>
        <c:choose>
            <c:when test="${follows.size() == 0}">
                <p>フォローしている人はまだいません</p>
            </c:when>
            <c:otherwise>
                <table id="follow_list">
                    <tr>
                        <th class="follow_employee_name">フォローしている人</th>
                        <th class="follow_action">操作</th>
                    </tr>
                    <c:forEach var="follow" items="${follows}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td class="follow_employee_name"><c:out value="${follow.followedEmployee.name}" /></td>
                            <td class="follow_action"><a href="<c:url value='?action=${actFol}&command=${commDst}&id=${follow.followedEmployee.id}' />">フォローを解除する</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>

        <div id="pagination">
           (全 ${followedEmployeeCount} 件)<br />
            <c:forEach var="i" begin="1" end="${((followedEmployeeCount - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actFol}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:param>
</c:import>