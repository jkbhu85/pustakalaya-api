(function a(){
    var timeoutHolder = 0;
    var TIME_PERIOD = 350;
    var header = document.getElementsByTagName('header')[0];
    var nav = document.getElementsByTagName('nav')[0];
    var content = document.getElementById('main-content');

    var testDiv = document.createElement('div');
    testDiv.style.position = 'fixed';
    testDiv.style.top = '0';
    testDiv.style.right = '0';
    testDiv.style.bottom = '0';
    testDiv.style.left = '0';

    function findWindowDimensions() {
        document.body.appendChild(testDiv);
        var w = testDiv.clientWidth;
        var h = testDiv.clientHeight;
        document.body.removeChild(testDiv);

        return {width: w, height: h};
    }

    function adjustUI() {
        var dims = findWindowDimensions();

        var hh = header.offsetHeight;
        var nh = nav.offsetHeight;
        var wh = dims.height;

        var ch = Math.floor(wh - hh - nh);

        if (ch <= 0) ch = 1;

        content.style.minHeight = ch + 'px';
    }

    window.addEventListener('resize', function(){
        if (timeoutHolder) window.clearTimeout(timeoutHolder);

        timeoutHolder = window.setTimeout(adjustUI, TIME_PERIOD);
    });

    window.setTimeout(
        function() { adjustUI(); },
        TIME_PERIOD
    );
})();