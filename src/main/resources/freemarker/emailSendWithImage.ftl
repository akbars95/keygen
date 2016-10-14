<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Send EMail</title>
</head>
<body>

<div style="width: 90%; margin: 0 auto">
    <h2>Hello, ${currentUser}!</h2>
    <p>You made request on ${requestDateTime} to generate key.</p>
    <p>Please go to this url:
        <span style="color: #2b542c; font-weight: bold;font-size: 12px;"><a href="${tempUrl}">Go to this link</span></a>
    </p>
    <p>When you go to by link, which in top, you need set this temp password: <input type="text" readonly style="    color: blue;
    border: none;border-bottom: 1px solid green;" id="tempPassword" value="${tempPassword}"></p>
    <p>
        <img style="width: 100%;" src="cid:${imageSrc}">
    </p>
    <p style="margin-top: 15px; color: red;">If this email you come by error, please delete this message!</p>
    <h2 align="right">Thanks, site administration!</h2>
</div>
</body>
</html>