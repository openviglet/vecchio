$.getJSON("https://api.github.com/users/openviglet/events", function (data) {
    var items = [];
    $.each(data, function (i, item) {
        $.each(item.payload.commits, function (i2, item2) {
            var formats = ['YYYY-MM-DDTHH:mm:ssZ', 'YYYY-MM-DDTHH:mm:ssZ'];
            var momentDate = moment(item.created_at, formats).locale('en').fromNow();
            $('#turingfeed').append($('<div style="margin-top:10px">').html($('<a target="_blank" style="font-size:small" href=https://github.com/' + item.repo.name + '>').html(item.repo.name.replace("openviglet/", "")))).append($('<div>').html('<a style="color:black" target="_blank" href="' + item2.url.replace("https://api.github.com/repos/openviglet/", "https://github.com/openviglet/").replace("commits", "commit") + '">' + item2.message.replace(/\n/g, "<br>") + '</a>')).append($('<small>').html('<a href="https://github.com/' + item.actor.login + '" target="_blank" >' + item.actor.display_login + '</a>' + " - " + momentDate));
        });
    });
});