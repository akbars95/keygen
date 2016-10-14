function getDate(date){
    var dateL = new Date();
    if(date){
       dateL = date;
    }
    /*
    * 10:10:25 10.12.10
    * */
    return setNullInt(dateL.getHours()) + ":" + setNullInt(dateL.getMinutes()) + ":" + setNullInt(dateL.getSeconds()) + " " +
        setNullInt(dateL.getDate()) + "." + setNullInt(dateL.getMonth()) + "." + dateL.getFullYear();
}

function setNullInt(number) {
    return (number > -1) ? ((number < 10) ? "0" : "") + number : "Error";
}

function selectAll(id)
{
    document.getElementById(id).focus();
    document.getElementById(id).select();
}