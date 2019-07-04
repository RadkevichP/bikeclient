function handleNameCodeFilter() {
    var nameKeyWord = document.getElementById('prjct_name_input').value;
    var codeKeyWord = document.getElementById('code_input').value;


    var data = {
        name: nameKeyWord,
        code: codeKeyWord
    };

    /*var keywords = [];
    keywords.push(nameKeyWord);
    keywords.push(codeKeyWord);*/

        ajaxSendData('/textFieldFilter', data);

}