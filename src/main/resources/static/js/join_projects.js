function joinProjects(url) {

    var parentName = document.getElementById('parent_name').value;

    var elements = $(".table_raw");
    console.log("Table_raw: " + elements);
    var checked = [];

    elements.each(function (index, element) {

        var checkbox = element.children[0].children[0].children[0].children[0].children[0];
        if (checkbox.checked) {
            checked.push(element.children[1].innerHTML);
        }
    });

    var data = {
        parentName: parentName,
        checkedProjects: checked
    };


    ajaxSendData(url, data);
}
