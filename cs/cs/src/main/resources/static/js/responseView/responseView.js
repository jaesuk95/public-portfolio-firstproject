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

function upload_(){
    var tweetmsg =  document.getElementById("content_").value;
    var string_check_space = tweetmsg.trim();

    if (string_check_space != ''){
        return;
    } else {
        alert("The content box needs to be filled");
        event.preventDefault();
    return false;
    }
}