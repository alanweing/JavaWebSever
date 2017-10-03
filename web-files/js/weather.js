const timeout = 30;
$(document).ready(() => {
    const progressBar = $('#progress-bar');
    doProgress(progressBar);
    setInterval(() => {
        doProgress(progressBar);
    }, timeout * 1000 + 3000);
});

const doProgress = e => {
    e.val(100);
    const decrement = setInterval(() => {
        e.val(e.val() - 100 / timeout / 25);
        if (e.val() <= 0) {
            clearInterval(decrement);
            doRequest();
        }
    }, 40);
};

const doRequest = () => {
    $.ajax({
        url: '/data/weather',
        method: 'get',
        headers: {
            Accept: 'text/html'
        }
    }).done(e => {
        for (let k of Object.keys(e))
            $('#'+k).html(e[k]);
    }).fail(err=>console.log(err));
};