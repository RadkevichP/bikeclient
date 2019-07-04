function archiveProjects(url) {
    var elements = $(".table_raw");
    console.log("Table_raw: " + elements);
    var checkedProjects = [];

    elements.each(function (index, element) {

        var checkbox = element.children[0].children[0].children[0].children[0].children[0];
        if (checkbox.checked) {
            checkedProjects.push(element.children[1].innerHTML);
        }
    });

    var data = {
        projectNames: checkedProjects
    };
    ajaxSendData(url, data);
}
