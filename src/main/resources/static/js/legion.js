var legion = {};

legion.showErrors = function(errorData) {
    legion.clearErrors();
    $.each(errorData, function (index, element) {
       var input = $("input[name=" + index + "]")[0];
       $(input).addClass("is-invalid");
       var divName = "errorArea_" + index;
       $(input).after("<div class='invalid-feedback' name='"+ divName +"'>" + element + "</div>");
    });
}

legion.clearErrors = function () {
    $("div[name^=errorArea_]").each(function (idx, ele) {
        $(ele).remove();
    });
    $("#mainForm").find("input").each(function (idx, ele) {
        $(ele).removeClass("is-invalid");
    });
}
