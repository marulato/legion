var legion = {};

legion.showErrors = function(errorData) {
    legion.clearErrors();
    $.each(errorData, function (index, element) {
       var input = $("input[name=" + index + "]")[0];
       $(input).css("border-color", "red");
       var divName = "errorArea_" + index;
       $(input).after("<div name='"+ divName +"'><span style='color: red; font-size: 14px'>" + element + "</span></div>");
    });
}

legion.clearErrors = function () {
    $("div[name^=errorArea_]").each(function (idx, ele) {
        $(ele).remove();
    });
    $("#mainForm").find("input").each(function (idx, ele) {
        $(ele).css("border-color", "#ddd");
    });
}
