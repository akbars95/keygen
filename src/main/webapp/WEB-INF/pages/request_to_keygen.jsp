<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="p_header.jsp" %>
<%@ include file="p_menu.jsp" %>
<div class="contentP">

<div class="container" ng-app="keygenApp" ng-controller="requestToKeygenCtrl" ng-cloak>

    <h3 align="center">
        <span ng-show="registrateResult && registrateResult.hasError == false">Email send success!</span>
        <span ng-show="registrateResult && registrateResult.hasError == true">Error! {{registrateResult.description}}</span>
    </h3>

    <form name="requestToKeygenForm" novalidate>
        <%--<div class="row">
            <div class="col-md-5"><label>Please choose</label></div>
            <div class="col-md-7">
                <label><input type="radio" name="chooseUsernameOrUserEmail" id="chooseUserEmail"
                              ng-click="chooseUsernameOrUserEmailF(true)" ng-model="chooseUsernameOrUserEmail" ng-value="true">User email</label>
                <label><input type="radio" name="chooseUsernameOrUserEmail" id="chooseUsername"
                              ng-click="chooseUsernameOrUserEmailF(false)" ng-model="chooseUsernameOrUserEmail" ng-value="false">Username</label>
            </div>
        </div>--%>
        <div class="row" ><%--ng-show="chooseUsernameOrUserEmail"--%>
            <div class="col-md-5"><label for="userEmail">User email</label></div>
            <div class="col-md-7">
                <input type="email" name="userEmail" ng-change="validation()" id="userEmail" ng-model="userEmail"
                       required ng-minlength="5" ng-maxlength="128">
                <span class="error" ng-show="requestToKeygenForm.userEmail.$error.required">Required!</span>
                <span class="error" ng-show="requestToKeygenForm.userEmail.$error.email">Not valid email!</span>
                <span class="error" ng-show="requestToKeygenForm.userEmail.$error.minlength">Min length-5</span>
                <span class="error" ng-show="requestToKeygenForm.userEmail.$error.maxlength">Max length-128</span>
                <span class="successOK" ng-show="!requestToKeygenForm.userEmail.$error.required &&
                !requestToKeygenForm.userEmail.$error.email && !requestToKeygenForm.userEmail.$error.minlength
                && !requestToKeygenForm.userEmail.$error.maxlength">OK</span>
            </div>
        </div>
        <div class="row" ><%--ng-show="!chooseUsernameOrUserEmail"--%>
            <div class="col-md-5"><label for="userName">User name</label></div>
            <div class="col-md-7">
                <input type="text" name="userName" ng-change="validation()" id="userName" ng-model="userName" required
                       ng-minlength="5" ng-maxlength="50" ng-pattern="/^[\w\.\-]{5,50}$/">
                <span class="error" ng-show="requestToKeygenForm.userName.$error.required">Required!</span>
                <span class="error" ng-show="requestToKeygenForm.userName.$error.minlength">Min length-5</span>
                <span class="error" ng-show="requestToKeygenForm.userName.$error.maxlength">Max length-50</span>
                <span class="error"
                      ng-show="requestToKeygenForm.userName.$error.pattern">Allowed chars:a-zA-Z0-9-.</span>
                <span class="successOK" ng-show="!requestToKeygenForm.userName.$error.required &&
                !requestToKeygenForm.userName.$error.minlength && !requestToKeygenForm.userName.$error.maxlength
                && !requestToKeygenForm.userName.$error.pattern">OK</span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5"><label for="userPassword">User password</label></div>
            <div class="col-md-7">
                <input type="password" name="userPassword" ng-change="validation()" id="userPassword"
                       ng-model="userPassword" required ng-minlength="5" ng-maxlength="50" ng-pattern="/^(?=.*[\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%-.,])[a-zA-Z0-9@#$%-.,]{5,50}$/"/>
                <span class="error" ng-show="requestToKeygenForm.userPassword.$error.required">Required!</span>
                <span class="error" ng-show="requestToKeygenForm.userPassword.$error.minlength">Min length-5</span>
                <span class="error" ng-show="requestToKeygenForm.userPassword.$error.maxlength">Max length-50</span>
                <span class="error" ng-show="requestToKeygenForm.userPassword.$error.pattern">Allowed chars:a-zA-Z0-9-.</span>
                <span class="successOK" ng-show="!requestToKeygenForm.userPassword.$error.required &&
                !requestToKeygenForm.userPassword.$error.minlength && !requestToKeygenForm.userPassword.$error.maxlength
                && !requestToKeygenForm.userPassword.$error.pattern">OK</span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5">
                <button type="button" class="btn btn-success" ng-disabled="!successValidation" ng-click="requestToKeygen()">Request</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="p_footer.jsp" %>