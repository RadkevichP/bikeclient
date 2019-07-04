function handleSelectFilter(id, url) {
    var selectedValue = document.getElementById(id).value;

    switch (id) {
        case 'lead_select':
            handleSelectLead(selectedValue, url);
            break;
        case 'status_select':
            handleSelectStatus(selectedValue, url);
            break;
        case 'customer_select':
            handleSelectCustomer(selectedValue, url);
            break;
        case 'date_select':
            handleSelectChanged(selectedValue, url);
            break;

    }
}

function handleSelectLead(value, url) {
    var data = {
        leadId: value
    };

    ajaxSendData(url, data);
}

function handleSelectStatus(value, url) {
    var data = {
        status: value
    };

    ajaxSendData(url, data);
}

function handleSelectChanged(value, url) {
    var data = {
        changedDate: value
    };

    ajaxSendData(url, data);
}

function handleSelectCustomer(value, url) {
    var data = {
        customerName: value
    };

    ajaxSendData(url, data);
}