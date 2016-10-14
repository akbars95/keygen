<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="p_header.jsp" %>
<%@ include file="p_menu.jsp" %>
<div class="contentP">

    <div ng-app="keygenApp" ng-controller="registerCtrl" ng-cloak>
        <h3 align="center">
            <span ng-show="registrateResult && registrateResult.hasError == false">Email send success!</span>
            <span ng-show="registrateResult && registrateResult.hasError == true">Error! {{registrateResult.description}}</span>
        </h3>

        <form name="emailRegisterForm" novalidate>
            <div class="row">
                <div class="col-md-5"><label for="userEmail">User email</label></div>
                <div class="col-md-7">
                    <input type="email" name="userEmail" id="userEmail" ng-model="userEmail" required ng-minlength="5" ng-maxlength="128">
                    <span class="error" ng-show="emailRegisterForm.userEmail.$error.required">Required!</span>
                    <span class="error" ng-show="emailRegisterForm.userEmail.$error.email">Not valid email!</span>
                    <span class="error" ng-show="emailRegisterForm.userEmail.$error.minlength">Min length-5</span>
                    <span class="error" ng-show="emailRegisterForm.userEmail.$error.maxlength">Max length-128</span>
                    <span class="successOK" ng-show="!emailRegisterForm.userEmail.$error.required &&
                !emailRegisterForm.userEmail.$error.email && !emailRegisterForm.userEmail.$error.minlength
                && !emailRegisterForm.userEmail.$error.maxlength">OK</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5"><label for="userName">User name</label></div>
                <div class="col-md-7">
                    <input type="text" name="userName" id="userName" ng-model="userName" required
                           ng-minlength="5" ng-maxlength="50" ng-pattern="/^[\w\.\-]{5,50}$/">
                    <span class="error" ng-show="emailRegisterForm.userName.$error.required">Required!</span>
                    <span class="error" ng-show="emailRegisterForm.userName.$error.minlength">Min length-5</span>
                    <span class="error" ng-show="emailRegisterForm.userName.$error.maxlength">Max length-50</span>
                    <span class="error" ng-show="emailRegisterForm.userName.$error.pattern">Allowed chars:a-zA-Z0-9-.</span>
                    <span class="successOK" ng-show="!emailRegisterForm.userName.$error.required &&
                !emailRegisterForm.userName.$error.minlength && !emailRegisterForm.userName.$error.maxlength
                && !emailRegisterForm.userName.$error.pattern">OK</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5"><label for="userPassword">User password</label></div>
                <div class="col-md-7">
                    <input type="password" name="userPassword" ng-change="checkPassword()" id="userPassword"
                           ng-model="userPassword" required ng-minlength="5" ng-maxlength="50" ng-pattern="/^(?=.*[\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%-.,])[a-zA-Z0-9@#$%-.,]{5,50}$/"/>
                    <span class="error" ng-show="emailRegisterForm.userPassword.$error.required">Required!</span>
                    <span class="error" ng-show="emailRegisterForm.userPassword.$error.minlength">Min length-5</span>
                    <span class="error" ng-show="emailRegisterForm.userPassword.$error.maxlength">Max length-50</span>
                    <span class="error" ng-show="emailRegisterForm.userPassword.$error.pattern">Allowed chars:a-zA-Z0-9-.</span>
                    <span class="successOK" ng-show="!emailRegisterForm.userPassword.$error.required &&
                !emailRegisterForm.userPassword.$error.minlength && !emailRegisterForm.userPassword.$error.maxlength
                && !emailRegisterForm.userPassword.$error.pattern">OK</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5"><label for="confirmUserPassword">Confirm User password</label></div>
                <div class="col-md-7">
                    <input type="password" name="confirmUserPassword" ng-change="checkPassword()" id="confirmUserPassword"
                           ng-model="confirmUserPassword" required ng-minlength="5" ng-maxlength="50" ng-pattern="/^(?=.*[\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%-.,])[a-zA-Z0-9@#$%-.,]{5,50}$/">
                    <span class="error" ng-show="emailRegisterForm.confirmUserPassword.$error.required">Required!</span>
                    <span class="error" ng-show="emailRegisterForm.confirmUserPassword.$error.minlength">Min length-5</span>
                    <span class="error" ng-show="emailRegisterForm.confirmUserPassword.$error.maxlength">Max length-50</span>
                    <span class="error" ng-show="emailRegisterForm.confirmUserPassword.$error.pattern">Allowed chars:a-zA-Z0-9-.</span>
                    <span class="successOK" ng-show="!emailRegisterForm.confirmUserPassword.$error.required &&
                !emailRegisterForm.confirmUserPassword.$error.minlength && !emailRegisterForm.confirmUserPassword.$error.maxlength
                && !emailRegisterForm.confirmUserPassword.$error.pattern">OK</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <span class="error" ng-show="!passwordStatusIsValid">Password and confirm password isn't equals!</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <button type="button" class="btn btn-success" ng-disabled="emailRegisterForm.$invalid || !passwordStatusIsValid"
                            ng-click="send()">Send
                    </button>
                    <button ng-hide="true" type="button" ng-click="defaultData(true)">Default
                    </button>
                </div>
            </div>
        </form>
    </div>

</div>
<%@ include file="p_footer.jsp" %>