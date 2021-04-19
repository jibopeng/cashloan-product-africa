// (function(doc, win) {
// 	var docEle = doc.documentElement,
// 		dpr = Math.min(win.devicePixelRatio, 3),
// 		scale = 1 / dpr,
// 		resizeEvent = 'orientationchange' in window ? 'orientationchange' : 'resize';
// 	// var metaEle = doc.createElement('meta');
// 	// metaEle.name = 'viewport';
// 	// metaEle.content = 'initial-scale=' + scale + ',maximum-scale=' + scale;
// 	// docEle.firstElementChild.appendChild(metaEle);

// 	var recalCulate = function() {
// 		var width = docEle.clientWidth;
// 		docEle.style.fontSize = 10 * (width / 375) + 'px';
//         console.log("docEle.style.fontSize",docEle.style.fontSize,win.devicePixelRatio)
// 	};

// 	recalCulate();

// 	if (!doc.addEventListener) return;
// 	win.addEventListener(resizeEvent, recalCulate, false);
// })(document, window);

 (function(n, e) {
        var t = n.documentElement,
            i = "orientationchange" in window ? "orientationchange" : "resize",
            d = function() {
                var n = t.clientWidth;
                if (n) {
                    var e = 100 * (n / 750);
                    e > 200 && (e = 200), t.style.fontSize = e + "px"
                }
            };
        n.addEventListener && (e.addEventListener(i, d, !1), n.addEventListener("DOMContentLoaded", d, !1))
    })(document, window); /*  |xGv00|62203cb4cc964d3e12d60d259446731c */