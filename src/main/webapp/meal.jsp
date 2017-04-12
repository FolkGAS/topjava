<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Meal</title>
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
<body style="background-color: lightgray; ">
<section>
    <c:set var="dateTime"
           value="${fn:contains(header['User-Agent'],'Chrome') ? meal.dateTime : fn:replace(meal.dateTime, 'T', ' ')}"/>

    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>

    <table style="position: absolute;
              top: 0;
              bottom: 0;
              left: 0;
              right: 0;
              height:100%;
              width:100%;">

        <tr valign="top">
            <td width="30%"></td>
            <td width="40%" bgcolor="white" style="padding: 10px">
                <h2 align="center"><a href="index.html">Home</a>
                    <br>
                    ${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>
                <form class="form-horizontal" method="post" action="meals">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="dateTime">Time:</label>
                        <div class="col-sm-10">
                            <input type="hidden"
                                   name="id"
                                   class="form-control"
                                   id="id"
                                   value="${meal.id}">

                            <input type="datetime-local"
                                   name="dateTime"
                                   class="form-control"
                                   id="dateTime"
                                   placeholder="Enter Time"
                                   pattern="[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]"
                                   title="yyyy-MM-dd HH:mm"
                                   value="${dateTime}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="description">Desc:</label>
                        <div class="col-sm-10">
                            <input type="text"
                                   name="description"
                                   class="form-control"
                                   id="description"
                                   placeholder="Enter Description"
                                   title="Description"
                                   value="${meal.description}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="calories">Calories:</label>
                        <div class="col-sm-10">
                            <input type="text"
                                   name="calories"
                                   class="form-control"
                                   id="calories"
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
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <button class="btn btn-default" onclick="window.history.back()">Cancel</button>
                        </div>
                    </div>
                </form>
            </td>
            <td width="30%"></td>
        </tr>
    </table>
</section>
</body>
</html>
