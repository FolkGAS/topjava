<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Meals list</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>

<body style="background-color: lightgray; ">
<%--<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>--%>
<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
<c:set var="dateTime"
       value="${fn:contains(header['User-Agent'],'Chrome') ? meal.dateTime : fn:replace(meal.dateTime, 'T', ' ')}"/>

<table style="position: absolute;
              top: 0;
              bottom: 0;
              left: 0;
              right: 0;
              height:100%;
              width:100%;">
    <tr valign="top">
        <td width="30%"></td>
        <td width="40%" bgcolor="white">
            <h2 align="center"><a href="index.html">Home</a></h2>

            <table class="tAddFilter" width="100%">
                <tr valign="bottom">
                    <%--ADD PAGE--%>
                    <td width="50%" style="padding: 10px">
                        <form class="form-horizontal" method="post" action="meals" onsubmit="">
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="DateTime">Time:</label>
                                <div class="col-sm-10">
                                    <input type="hidden"
                                           name="ID"
                                           class="form-control"
                                           id="ID"
                                           value="${meal.id}">

                                    <input type="datetime-local"
                                           name="DateTime"
                                           class="form-control"
                                           id="DateTime"
                                           placeholder="Enter Time"
                                           pattern="[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]"
                                           title="yyyy-MM-dd HH:mm"
                                           value="${dateTime}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="Description">Desc:</label>
                                <div class="col-sm-10">
                                    <input type="text"
                                           name="Description"
                                           class="form-control"
                                           id="Description"
                                           placeholder="Enter Description"
                                           title="Description"
                                           value="${meal.description}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="Calories">Calories:</label>
                                <div class="col-sm-10">
                                    <input type="text"
                                           name="Calories"
                                           class="form-control"
                                           id="Calories"
                                           placeholder="Enter Calories"
                                           pattern="[0-9]+\d*"
                                           title="positive number"
                                           value="${meal.calories}">
                                </div>
                            </div>
                            <c:set var="btn" value="${meal.id > 0 ? 'edit' : 'add'}"/>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-default">${btn}</button>
                                </div>
                            </div>
                        </form>
                    </td>

                    <%--FILTER PAGE--%>
                    <td width="50%" style="padding: 10px">
                        <form class="form-horizontal" method="post">
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="From">From:</label>
                                <div class="col-sm-10">
                                    <input type="text"
                                           name="From"
                                           class="form-control"
                                           id="From"
                                           placeholder="Enter start time"
                                           pattern="^([0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
                                           title="hh:mm">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="To">To:</label>
                                <div class="col-sm-10">
                                    <input type="text"
                                           name="To"
                                           class="form-control"
                                           id="To"
                                           placeholder="Enter end time"
                                           pattern="^([0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
                                           title="hh:mm">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-default">Filter</button>
                                </div>
                            </div>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <h2>Meal list</h2>
                    </td>
                </tr>
                <%--<tr>--%>
                <%--&lt;%&ndash;PAGING MENU&ndash;%&gt;--%>
                <%--<td colspan="2">--%>
                <%--PAGING--%>
                <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <td colspan="2">
                        <table class="tAddFilter" width="100%">
                            <tr>
                                <td colspan="2">
                                    <table class="table table-striped display dataTable no-footer">
                                        <tr>
                                            <th width="20%">Date & time</th>
                                            <th width="20%">Description</th>
                                            <th width="20%">Calories</th>
                                            <th width="20%">Edit</th>
                                            <th width="20%">Delete</th>
                                        </tr>
                                        <c:if test="${!empty meals}">
                                            <c:forEach items="${meals}" var="meal">
                                                <c:if test="${meal.id > 0}">
                                                    <tr class="${meal.exceed ? "danger" : "success"}">
                                                        <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
                                                        <td>${meal.description}</td>
                                                        <td>${meal.calories}</td>
                                                        <td><a href="<c:url value='?edit=${meal.id}'/>"
                                                               class="btn btn-primary btn-xs"
                                                               role="button">Edit</a>
                                                        <td><a href="<c:url value='?remove=${meal.id}'/>"
                                                               target="_self"
                                                               class="btn btn-danger btn-xs"
                                                               role="button">Delete</a>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%--<tr>--%>
                <%--&lt;%&ndash;PAGING MENU&ndash;%&gt;--%>
                <%--<td colspan="2">--%>
                <%--PAGING--%>
                <%--</td>--%>
                <%--</tr>--%>
            </table>
        </td>
        <td width="30%"></td>
    </tr>
</table>

</body>
</html>
