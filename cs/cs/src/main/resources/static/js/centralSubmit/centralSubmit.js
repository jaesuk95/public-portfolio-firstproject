function upload(){
    var tweetmsg =  document.getElementById("tweet").value;
    var string_check_space = tweetmsg.trim();

    if (string_check_space != ''){
        return;
    } else {
        alert("The content box needs to be filled");
        event.preventDefault();
    return false;
    }
}

function upload(){
    var tweetmsg =  document.getElementById("content").value;
    var string_check_space = tweetmsg.trim();

    if (string_check_space != ''){
        return;
    } else {
        alert("The content box needs to be filled");
        event.preventDefault();
    return false;
    }
}


