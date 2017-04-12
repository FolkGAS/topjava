<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="css/style.css">


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
<body style="background-color: lightgray;">
<section>
    <jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.to.MealWithExceed>"/>

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
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <h2>Meal list</h2>
                            <a href="meals?action=create">Add Meal</a>
                        </td>
                    </tr>
                    <%--FILTER PAGE--%>
                    <form class="form-horizontal" method="get">
                        <tr>
                            <td width="50%" style="padding: 10px">
                                DATE
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="dateFrom">From:</label>
                                    <div class="col-sm-10">
                                        <input type="hidden"
                                               name="action"
                                               class="form-control"
                                               id="action"
                                               value="filter">

                                        <input type="date"
                                               name="dateFrom"
                                               class="form-control"
                                               id="dateFrom"
                                               placeholder="Enter start date"
                                               pattern="[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])"
                                               title="yyyy-mm-dd">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="dateTo">To:</label>
                                    <div class="col-sm-10">
                                        <input type="date"
                                               name="dateTo"
                                               class="form-control"
                                               id="dateTo"
                                               placeholder="Enter end date"
                                               pattern="[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])"
                                               title="yyyy-mm-dd">
                                    </div>
                                </div>
                                <%--<div class="form-group">--%>
                                <%--<div class="col-sm-offset-2 col-sm-10">--%>
                                <%--<button type="submit" class="btn btn-default">Filter</button>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                            </td>
                            <td width="50%" style="padding: 10px">
                                TIME
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="timeFrom">From:</label>
                                    <div class="col-sm-10">
                                        <input type="time"
                                               name="timeFrom"
                                               class="form-control"
                                               id="timeFrom"
                                               placeholder="Enter start time"
                                               pattern="^([0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
                                               title="hh:mm">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="timeTo">To:</label>
                                    <div class="col-sm-10">
                                        <input type="time"
                                               name="timeTo"
                                               class="form-control"
                                               id="timeTo"
                                               placeholder="Enter end time"
                                               pattern="^([0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"
                                               title="hh:mm">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <button class="btn btn-default" onclick="window.history.back()">Cancel</button>
                            </td>
                            <td align="center">
                                <button type="submit" class="btn btn-default">Filter</button>
                            </td>
                        </tr>
                    </form>
                    <%--<tr>--%>
                    <%--&lt;%&ndash;PAGING MENU&ndash;%&gt;--%>
                    <%--<td colspan="2">--%>
                    <%--PAGING--%>
                    <%--</td>--%>
                    <%--</tr>--%>
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
                                                <td><a href="<c:url value='?action=update&id=${meal.id}'/>"
                                                       class="btn btn-primary btn-xs"
                                                       role="button">Update</a>
                                                <td><a href="<c:url value='?action=delete&id=${meal.id}'/>"
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
</section>
</body>
</html>