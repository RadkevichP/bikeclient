function handleSettingsMenu() {
    var settingsButton = document.getElementById("st_menu_1");
    console.log(settingsButton.style.display);
    if (settingsButton.style.display === 'block') {
        return;
    }

    var filterName = document.getElementById("filter_name");
    var filterCode = document.getElementById("filter_code");
    var filterLead = document.getElementById("filter_lead");
    var filterStatus = document.getElementById("filter_status");
    var filterCustomer = document.getElementById("filter_customer");
    var filterChanged = document.getElementById("filter_changed");

    var filters = [filterName, filterCode, filterLead, filterStatus, filterCustomer, filterChanged];

    var checked = [];

    filters.forEach(function (value) {
        if (value.checked) checked.push(value.id);
    });

    var data = {
        checked: checked
    };

    ajaxSendData('/filterSettingsMenu', data);
}