function main() {
    loadHeader();
    loadFooter();
    loadNav();
    ui();
}

main();

function loadHeader() {
    const header = document.querySelector('#header');

    if (header) {
        const url = window.location.href;
        const searchKey = '/ptkdocs/';
        const docPos = url.indexOf(searchKey) + 1;

        const newUrl = url.substr(0, url.indexOf(searchKey) + searchKey.length) + 'index.html';

        header.innerHTML = '<h1><a href="' + newUrl + '">Pustakalaya</a></h1>';
    }
}

function loadFooter() {
    const footer = document.querySelector('#footer');

    if (footer) {
        footer.innerHTML = '<div style="text-align:center;">&copy; Jitendra Kumar</div>';
    }
}

function loadNav() {
    const navElm = document.getElementsByTagName("nav")[0];

    if (navElm) {
        navElm.style.display = 'hidden';
    }
}

function ui() {
    let timeoutHolder = 0;
    const TIME_PERIOD = 200;
    const header = document.querySelector('#header');
    const footer = document.querySelector('#footer');
    const nav = document.querySelector('nav');
    const content = document.querySelector('.main-content');

    if (!content) return;

    function findWindowDimensions() {
        const testDiv = document.createElement('div');
        testDiv.style.position = 'fixed';
        testDiv.style.top = '0';
        testDiv.style.right = '0';
        testDiv.style.bottom = '0';
        testDiv.style.left = '0';

        document.body.appendChild(testDiv);
        var w = testDiv.clientWidth;
        var h = testDiv.clientHeight;
        document.body.removeChild(testDiv);

        return { width: w, height: h };
    }

    function adjustUI() {
        const dims = findWindowDimensions();

        const hh = (header ? header.offsetHeight : 0);
        const fh = (footer ? footer.offsetHeight : 0);
        const nh = (nav ? nav.offsetHeight : 0);
        const wh = dims.height;

        var ch = Math.floor(wh - hh - nh -fh);

        if (ch <= 0) ch = 1;

        content.style.minHeight = ch + 'px';
    }

    window.addEventListener('resize', function () {
        if (timeoutHolder) window.clearTimeout(timeoutHolder);

        timeoutHolder = window.setTimeout(adjustUI, TIME_PERIOD);
    });

    window.setTimeout(
        function () { adjustUI(); },
        TIME_PERIOD
    );
};