<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="p_header.jsp" %>
<%@ include file="p_menu.jsp" %>
<div class="contentP">

<div ng-app="keygenApp" ng-controller="getKeyCtrl">
    <h3 align="center">
        <span ng-show="registrateResult && registrateResult.hasError == false">Key success received!</span>
        <span ng-show="registrateResult && registrateResult.hasError == true">Error! {{registrateResult.description}}</span>
    </h3>
    <form name="getKeyForm" novalidate>
        <div class="row">
            <div class="col-md-5"><label for="userEmail">User email</label></div>
            <div class="col-md-7">
                <input type="email" name="userEmail" id="userEmail" ng-change="validation()" ng-model="userEmail">
                <span class="error" ng-show="getKeyForm.userEmail.$error.email">Not valid email!</span>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5"><label for="userName">User name</label></div>
            <div class="col-md-7">
                <input type="text" name="userName" id="userName" ng-model="userName" ng-change="validation()">
            </div>
        </div>
        <div class="row">
            <div class="col-md-5"><label for="tempPassword">Temp password</label></div>
            <div class="col-md-7">
                <input type="password" name="tempPassword" id="tempPassword" ng-change="validation()"
                       ng-model="tempPassword" required>
                <span class="error" ng-show="getKeyForm.tempPassword.$error.required">Required!</span>
            </div>
        </div>
        <div class="row" ng-show="responseKey">
            <div class="col-md-5"><label for="responseKey">Your key</label></div>
            <div class="col-md-7">
                <input type="text" name="responseKey" id="responseKey"
                       ng-model="responseKey" readonly onclick="selectAll('responseKey')">
                <button type="button" onclick="selectAll('responseKey')">Select all text</button>
            </div>
        </div>
        <div class="row">
            <div class="col-md-5">
                <button type="button" ng-disabled="!successValidation" ng-click="sendGetKey()">Get key</button>
            </div>
        </div>
    </form>
</div>

<%@ include file="p_footer.jsp" %>