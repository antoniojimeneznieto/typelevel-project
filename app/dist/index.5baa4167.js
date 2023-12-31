// modules are defined as an array
// [ module function, map of requires ]
//
// map of requires is short require name -> numeric require
//
// anything defined in a previous bundle is accessed via the
// orig method which is the require for previous bundles

(function (modules, entry, mainEntry, parcelRequireName, globalName) {
  /* eslint-disable no-undef */
  var globalObject =
    typeof globalThis !== 'undefined'
      ? globalThis
      : typeof self !== 'undefined'
      ? self
      : typeof window !== 'undefined'
      ? window
      : typeof global !== 'undefined'
      ? global
      : {};
  /* eslint-enable no-undef */

  // Save the require from previous bundle to this closure if any
  var previousRequire =
    typeof globalObject[parcelRequireName] === 'function' &&
    globalObject[parcelRequireName];

  var cache = previousRequire.cache || {};
  // Do not use `require` to prevent Webpack from trying to bundle this call
  var nodeRequire =
    typeof module !== 'undefined' &&
    typeof module.require === 'function' &&
    module.require.bind(module);

  function newRequire(name, jumped) {
    if (!cache[name]) {
      if (!modules[name]) {
        // if we cannot find the module within our internal map or
        // cache jump to the current global require ie. the last bundle
        // that was added to the page.
        var currentRequire =
          typeof globalObject[parcelRequireName] === 'function' &&
          globalObject[parcelRequireName];
        if (!jumped && currentRequire) {
          return currentRequire(name, true);
        }

        // If there are other bundles on this page the require from the
        // previous one is saved to 'previousRequire'. Repeat this as
        // many times as there are bundles until the module is found or
        // we exhaust the require chain.
        if (previousRequire) {
          return previousRequire(name, true);
        }

        // Try the node require function if it exists.
        if (nodeRequire && typeof name === 'string') {
          return nodeRequire(name);
        }

        var err = new Error("Cannot find module '" + name + "'");
        err.code = 'MODULE_NOT_FOUND';
        throw err;
      }

      localRequire.resolve = resolve;
      localRequire.cache = {};

      var module = (cache[name] = new newRequire.Module(name));

      modules[name][0].call(
        module.exports,
        localRequire,
        module,
        module.exports,
        this
      );
    }

    return cache[name].exports;

    function localRequire(x) {
      var res = localRequire.resolve(x);
      return res === false ? {} : newRequire(res);
    }

    function resolve(x) {
      var id = modules[name][1][x];
      return id != null ? id : x;
    }
  }

  function Module(moduleName) {
    this.id = moduleName;
    this.bundle = newRequire;
    this.exports = {};
  }

  newRequire.isParcelRequire = true;
  newRequire.Module = Module;
  newRequire.modules = modules;
  newRequire.cache = cache;
  newRequire.parent = previousRequire;
  newRequire.register = function (id, exports) {
    modules[id] = [
      function (require, module) {
        module.exports = exports;
      },
      {},
    ];
  };

  Object.defineProperty(newRequire, 'root', {
    get: function () {
      return globalObject[parcelRequireName];
    },
  });

  globalObject[parcelRequireName] = newRequire;

  for (var i = 0; i < entry.length; i++) {
    newRequire(entry[i]);
  }

  if (mainEntry) {
    // Expose entry point to Node, AMD or browser globals
    // Based on https://github.com/ForbesLindesay/umd/blob/master/template.js
    var mainExports = newRequire(mainEntry);

    // CommonJS
    if (typeof exports === 'object' && typeof module !== 'undefined') {
      module.exports = mainExports;

      // RequireJS
    } else if (typeof define === 'function' && define.amd) {
      define(function () {
        return mainExports;
      });

      // <script>
    } else if (globalName) {
      this[globalName] = mainExports;
    }
  }
})({"hF8lp":[function(require,module,exports) {
var global = arguments[3];
var HMR_HOST = null;
var HMR_PORT = null;
var HMR_SECURE = false;
var HMR_ENV_HASH = "d6ea1d42532a7575";
module.bundle.HMR_BUNDLE_ID = "138b6a135baa4167";
"use strict";
/* global HMR_HOST, HMR_PORT, HMR_ENV_HASH, HMR_SECURE, chrome, browser, __parcel__import__, __parcel__importScripts__, ServiceWorkerGlobalScope */ /*::
import type {
  HMRAsset,
  HMRMessage,
} from '@parcel/reporter-dev-server/src/HMRServer.js';
interface ParcelRequire {
  (string): mixed;
  cache: {|[string]: ParcelModule|};
  hotData: {|[string]: mixed|};
  Module: any;
  parent: ?ParcelRequire;
  isParcelRequire: true;
  modules: {|[string]: [Function, {|[string]: string|}]|};
  HMR_BUNDLE_ID: string;
  root: ParcelRequire;
}
interface ParcelModule {
  hot: {|
    data: mixed,
    accept(cb: (Function) => void): void,
    dispose(cb: (mixed) => void): void,
    // accept(deps: Array<string> | string, cb: (Function) => void): void,
    // decline(): void,
    _acceptCallbacks: Array<(Function) => void>,
    _disposeCallbacks: Array<(mixed) => void>,
  |};
}
interface ExtensionContext {
  runtime: {|
    reload(): void,
    getURL(url: string): string;
    getManifest(): {manifest_version: number, ...};
  |};
}
declare var module: {bundle: ParcelRequire, ...};
declare var HMR_HOST: string;
declare var HMR_PORT: string;
declare var HMR_ENV_HASH: string;
declare var HMR_SECURE: boolean;
declare var chrome: ExtensionContext;
declare var browser: ExtensionContext;
declare var __parcel__import__: (string) => Promise<void>;
declare var __parcel__importScripts__: (string) => Promise<void>;
declare var globalThis: typeof self;
declare var ServiceWorkerGlobalScope: Object;
*/ var OVERLAY_ID = "__parcel__error__overlay__";
var OldModule = module.bundle.Module;
function Module(moduleName) {
    OldModule.call(this, moduleName);
    this.hot = {
        data: module.bundle.hotData[moduleName],
        _acceptCallbacks: [],
        _disposeCallbacks: [],
        accept: function(fn) {
            this._acceptCallbacks.push(fn || function() {});
        },
        dispose: function(fn) {
            this._disposeCallbacks.push(fn);
        }
    };
    module.bundle.hotData[moduleName] = undefined;
}
module.bundle.Module = Module;
module.bundle.hotData = {};
var checkedAssets /*: {|[string]: boolean|} */ , assetsToDispose /*: Array<[ParcelRequire, string]> */ , assetsToAccept /*: Array<[ParcelRequire, string]> */ ;
function getHostname() {
    return HMR_HOST || (location.protocol.indexOf("http") === 0 ? location.hostname : "localhost");
}
function getPort() {
    return HMR_PORT || location.port;
}
// eslint-disable-next-line no-redeclare
var parent = module.bundle.parent;
if ((!parent || !parent.isParcelRequire) && typeof WebSocket !== "undefined") {
    var hostname = getHostname();
    var port = getPort();
    var protocol = HMR_SECURE || location.protocol == "https:" && !/localhost|127.0.0.1|0.0.0.0/.test(hostname) ? "wss" : "ws";
    var ws = new WebSocket(protocol + "://" + hostname + (port ? ":" + port : "") + "/");
    // Web extension context
    var extCtx = typeof chrome === "undefined" ? typeof browser === "undefined" ? null : browser : chrome;
    // Safari doesn't support sourceURL in error stacks.
    // eval may also be disabled via CSP, so do a quick check.
    var supportsSourceURL = false;
    try {
        (0, eval)('throw new Error("test"); //# sourceURL=test.js');
    } catch (err) {
        supportsSourceURL = err.stack.includes("test.js");
    }
    // $FlowFixMe
    ws.onmessage = async function(event /*: {data: string, ...} */ ) {
        checkedAssets = {} /*: {|[string]: boolean|} */ ;
        assetsToAccept = [];
        assetsToDispose = [];
        var data /*: HMRMessage */  = JSON.parse(event.data);
        if (data.type === "update") {
            // Remove error overlay if there is one
            if (typeof document !== "undefined") removeErrorOverlay();
            let assets = data.assets.filter((asset)=>asset.envHash === HMR_ENV_HASH);
            // Handle HMR Update
            let handled = assets.every((asset)=>{
                return asset.type === "css" || asset.type === "js" && hmrAcceptCheck(module.bundle.root, asset.id, asset.depsByBundle);
            });
            if (handled) {
                console.clear();
                // Dispatch custom event so other runtimes (e.g React Refresh) are aware.
                if (typeof window !== "undefined" && typeof CustomEvent !== "undefined") window.dispatchEvent(new CustomEvent("parcelhmraccept"));
                await hmrApplyUpdates(assets);
                // Dispose all old assets.
                let processedAssets = {} /*: {|[string]: boolean|} */ ;
                for(let i = 0; i < assetsToDispose.length; i++){
                    let id = assetsToDispose[i][1];
                    if (!processedAssets[id]) {
                        hmrDispose(assetsToDispose[i][0], id);
                        processedAssets[id] = true;
                    }
                }
                // Run accept callbacks. This will also re-execute other disposed assets in topological order.
                processedAssets = {};
                for(let i = 0; i < assetsToAccept.length; i++){
                    let id = assetsToAccept[i][1];
                    if (!processedAssets[id]) {
                        hmrAccept(assetsToAccept[i][0], id);
                        processedAssets[id] = true;
                    }
                }
            } else fullReload();
        }
        if (data.type === "error") {
            // Log parcel errors to console
            for (let ansiDiagnostic of data.diagnostics.ansi){
                let stack = ansiDiagnostic.codeframe ? ansiDiagnostic.codeframe : ansiDiagnostic.stack;
                console.error("\uD83D\uDEA8 [parcel]: " + ansiDiagnostic.message + "\n" + stack + "\n\n" + ansiDiagnostic.hints.join("\n"));
            }
            if (typeof document !== "undefined") {
                // Render the fancy html overlay
                removeErrorOverlay();
                var overlay = createErrorOverlay(data.diagnostics.html);
                // $FlowFixMe
                document.body.appendChild(overlay);
            }
        }
    };
    ws.onerror = function(e) {
        console.error(e.message);
    };
    ws.onclose = function() {
        console.warn("[parcel] \uD83D\uDEA8 Connection to the HMR server was lost");
    };
}
function removeErrorOverlay() {
    var overlay = document.getElementById(OVERLAY_ID);
    if (overlay) {
        overlay.remove();
        console.log("[parcel] ✨ Error resolved");
    }
}
function createErrorOverlay(diagnostics) {
    var overlay = document.createElement("div");
    overlay.id = OVERLAY_ID;
    let errorHTML = '<div style="background: black; opacity: 0.85; font-size: 16px; color: white; position: fixed; height: 100%; width: 100%; top: 0px; left: 0px; padding: 30px; font-family: Menlo, Consolas, monospace; z-index: 9999;">';
    for (let diagnostic of diagnostics){
        let stack = diagnostic.frames.length ? diagnostic.frames.reduce((p, frame)=>{
            return `${p}
<a href="/__parcel_launch_editor?file=${encodeURIComponent(frame.location)}" style="text-decoration: underline; color: #888" onclick="fetch(this.href); return false">${frame.location}</a>
${frame.code}`;
        }, "") : diagnostic.stack;
        errorHTML += `
      <div>
        <div style="font-size: 18px; font-weight: bold; margin-top: 20px;">
          🚨 ${diagnostic.message}
        </div>
        <pre>${stack}</pre>
        <div>
          ${diagnostic.hints.map((hint)=>"<div>\uD83D\uDCA1 " + hint + "</div>").join("")}
        </div>
        ${diagnostic.documentation ? `<div>📝 <a style="color: violet" href="${diagnostic.documentation}" target="_blank">Learn more</a></div>` : ""}
      </div>
    `;
    }
    errorHTML += "</div>";
    overlay.innerHTML = errorHTML;
    return overlay;
}
function fullReload() {
    if ("reload" in location) location.reload();
    else if (extCtx && extCtx.runtime && extCtx.runtime.reload) extCtx.runtime.reload();
}
function getParents(bundle, id) /*: Array<[ParcelRequire, string]> */ {
    var modules = bundle.modules;
    if (!modules) return [];
    var parents = [];
    var k, d, dep;
    for(k in modules)for(d in modules[k][1]){
        dep = modules[k][1][d];
        if (dep === id || Array.isArray(dep) && dep[dep.length - 1] === id) parents.push([
            bundle,
            k
        ]);
    }
    if (bundle.parent) parents = parents.concat(getParents(bundle.parent, id));
    return parents;
}
function updateLink(link) {
    var href = link.getAttribute("href");
    if (!href) return;
    var newLink = link.cloneNode();
    newLink.onload = function() {
        if (link.parentNode !== null) // $FlowFixMe
        link.parentNode.removeChild(link);
    };
    newLink.setAttribute("href", // $FlowFixMe
    href.split("?")[0] + "?" + Date.now());
    // $FlowFixMe
    link.parentNode.insertBefore(newLink, link.nextSibling);
}
var cssTimeout = null;
function reloadCSS() {
    if (cssTimeout) return;
    cssTimeout = setTimeout(function() {
        var links = document.querySelectorAll('link[rel="stylesheet"]');
        for(var i = 0; i < links.length; i++){
            // $FlowFixMe[incompatible-type]
            var href /*: string */  = links[i].getAttribute("href");
            var hostname = getHostname();
            var servedFromHMRServer = hostname === "localhost" ? new RegExp("^(https?:\\/\\/(0.0.0.0|127.0.0.1)|localhost):" + getPort()).test(href) : href.indexOf(hostname + ":" + getPort());
            var absolute = /^https?:\/\//i.test(href) && href.indexOf(location.origin) !== 0 && !servedFromHMRServer;
            if (!absolute) updateLink(links[i]);
        }
        cssTimeout = null;
    }, 50);
}
function hmrDownload(asset) {
    if (asset.type === "js") {
        if (typeof document !== "undefined") {
            let script = document.createElement("script");
            script.src = asset.url + "?t=" + Date.now();
            if (asset.outputFormat === "esmodule") script.type = "module";
            return new Promise((resolve, reject)=>{
                var _document$head;
                script.onload = ()=>resolve(script);
                script.onerror = reject;
                (_document$head = document.head) === null || _document$head === void 0 || _document$head.appendChild(script);
            });
        } else if (typeof importScripts === "function") {
            // Worker scripts
            if (asset.outputFormat === "esmodule") return import(asset.url + "?t=" + Date.now());
            else return new Promise((resolve, reject)=>{
                try {
                    importScripts(asset.url + "?t=" + Date.now());
                    resolve();
                } catch (err) {
                    reject(err);
                }
            });
        }
    }
}
async function hmrApplyUpdates(assets) {
    global.parcelHotUpdate = Object.create(null);
    let scriptsToRemove;
    try {
        // If sourceURL comments aren't supported in eval, we need to load
        // the update from the dev server over HTTP so that stack traces
        // are correct in errors/logs. This is much slower than eval, so
        // we only do it if needed (currently just Safari).
        // https://bugs.webkit.org/show_bug.cgi?id=137297
        // This path is also taken if a CSP disallows eval.
        if (!supportsSourceURL) {
            let promises = assets.map((asset)=>{
                var _hmrDownload;
                return (_hmrDownload = hmrDownload(asset)) === null || _hmrDownload === void 0 ? void 0 : _hmrDownload.catch((err)=>{
                    // Web extension bugfix for Chromium
                    // https://bugs.chromium.org/p/chromium/issues/detail?id=1255412#c12
                    if (extCtx && extCtx.runtime && extCtx.runtime.getManifest().manifest_version == 3) {
                        if (typeof ServiceWorkerGlobalScope != "undefined" && global instanceof ServiceWorkerGlobalScope) {
                            extCtx.runtime.reload();
                            return;
                        }
                        asset.url = extCtx.runtime.getURL("/__parcel_hmr_proxy__?url=" + encodeURIComponent(asset.url + "?t=" + Date.now()));
                        return hmrDownload(asset);
                    }
                    throw err;
                });
            });
            scriptsToRemove = await Promise.all(promises);
        }
        assets.forEach(function(asset) {
            hmrApply(module.bundle.root, asset);
        });
    } finally{
        delete global.parcelHotUpdate;
        if (scriptsToRemove) scriptsToRemove.forEach((script)=>{
            if (script) {
                var _document$head2;
                (_document$head2 = document.head) === null || _document$head2 === void 0 || _document$head2.removeChild(script);
            }
        });
    }
}
function hmrApply(bundle /*: ParcelRequire */ , asset /*:  HMRAsset */ ) {
    var modules = bundle.modules;
    if (!modules) return;
    if (asset.type === "css") reloadCSS();
    else if (asset.type === "js") {
        let deps = asset.depsByBundle[bundle.HMR_BUNDLE_ID];
        if (deps) {
            if (modules[asset.id]) {
                // Remove dependencies that are removed and will become orphaned.
                // This is necessary so that if the asset is added back again, the cache is gone, and we prevent a full page reload.
                let oldDeps = modules[asset.id][1];
                for(let dep in oldDeps)if (!deps[dep] || deps[dep] !== oldDeps[dep]) {
                    let id = oldDeps[dep];
                    let parents = getParents(module.bundle.root, id);
                    if (parents.length === 1) hmrDelete(module.bundle.root, id);
                }
            }
            if (supportsSourceURL) // Global eval. We would use `new Function` here but browser
            // support for source maps is better with eval.
            (0, eval)(asset.output);
            // $FlowFixMe
            let fn = global.parcelHotUpdate[asset.id];
            modules[asset.id] = [
                fn,
                deps
            ];
        } else if (bundle.parent) hmrApply(bundle.parent, asset);
    }
}
function hmrDelete(bundle, id) {
    let modules = bundle.modules;
    if (!modules) return;
    if (modules[id]) {
        // Collect dependencies that will become orphaned when this module is deleted.
        let deps = modules[id][1];
        let orphans = [];
        for(let dep in deps){
            let parents = getParents(module.bundle.root, deps[dep]);
            if (parents.length === 1) orphans.push(deps[dep]);
        }
        // Delete the module. This must be done before deleting dependencies in case of circular dependencies.
        delete modules[id];
        delete bundle.cache[id];
        // Now delete the orphans.
        orphans.forEach((id)=>{
            hmrDelete(module.bundle.root, id);
        });
    } else if (bundle.parent) hmrDelete(bundle.parent, id);
}
function hmrAcceptCheck(bundle /*: ParcelRequire */ , id /*: string */ , depsByBundle /*: ?{ [string]: { [string]: string } }*/ ) {
    if (hmrAcceptCheckOne(bundle, id, depsByBundle)) return true;
    // Traverse parents breadth first. All possible ancestries must accept the HMR update, or we'll reload.
    let parents = getParents(module.bundle.root, id);
    let accepted = false;
    while(parents.length > 0){
        let v = parents.shift();
        let a = hmrAcceptCheckOne(v[0], v[1], null);
        if (a) // If this parent accepts, stop traversing upward, but still consider siblings.
        accepted = true;
        else {
            // Otherwise, queue the parents in the next level upward.
            let p = getParents(module.bundle.root, v[1]);
            if (p.length === 0) {
                // If there are no parents, then we've reached an entry without accepting. Reload.
                accepted = false;
                break;
            }
            parents.push(...p);
        }
    }
    return accepted;
}
function hmrAcceptCheckOne(bundle /*: ParcelRequire */ , id /*: string */ , depsByBundle /*: ?{ [string]: { [string]: string } }*/ ) {
    var modules = bundle.modules;
    if (!modules) return;
    if (depsByBundle && !depsByBundle[bundle.HMR_BUNDLE_ID]) {
        // If we reached the root bundle without finding where the asset should go,
        // there's nothing to do. Mark as "accepted" so we don't reload the page.
        if (!bundle.parent) return true;
        return hmrAcceptCheck(bundle.parent, id, depsByBundle);
    }
    if (checkedAssets[id]) return true;
    checkedAssets[id] = true;
    var cached = bundle.cache[id];
    assetsToDispose.push([
        bundle,
        id
    ]);
    if (!cached || cached.hot && cached.hot._acceptCallbacks.length) {
        assetsToAccept.push([
            bundle,
            id
        ]);
        return true;
    }
}
function hmrDispose(bundle /*: ParcelRequire */ , id /*: string */ ) {
    var cached = bundle.cache[id];
    bundle.hotData[id] = {};
    if (cached && cached.hot) cached.hot.data = bundle.hotData[id];
    if (cached && cached.hot && cached.hot._disposeCallbacks.length) cached.hot._disposeCallbacks.forEach(function(cb) {
        cb(bundle.hotData[id]);
    });
    delete bundle.cache[id];
}
function hmrAccept(bundle /*: ParcelRequire */ , id /*: string */ ) {
    // Execute the module.
    bundle(id);
    // Run the accept callbacks in the new version of the module.
    var cached = bundle.cache[id];
    if (cached && cached.hot && cached.hot._acceptCallbacks.length) cached.hot._acceptCallbacks.forEach(function(cb) {
        var assetsToAlsoAccept = cb(function() {
            return getParents(module.bundle.root, id);
        });
        if (assetsToAlsoAccept && assetsToAccept.length) {
            assetsToAlsoAccept.forEach(function(a) {
                hmrDispose(a[0], a[1]);
            });
            // $FlowFixMe[method-unbinding]
            assetsToAccept.push.apply(assetsToAccept, assetsToAlsoAccept);
        }
    });
}

},{}],"igcvL":[function(require,module,exports) {
var _appFastoptJs = require("./target/scala-3.2.1/app-fastopt.js");
(0, _appFastoptJs.antonioApp)().doSomething("app");

},{"./target/scala-3.2.1/app-fastopt.js":"imsUD"}],"imsUD":[function(require,module,exports) {
"use strict";
var $linkingInfo = Object.freeze({
    "esVersion": 6,
    "assumingES6": true,
    "productionMode": false,
    "linkerVersion": "1.12.0",
    "fileLevelThis": this
});
var $getOwnPropertyDescriptors = Object.getOwnPropertyDescriptors || (()=>{
    var ownKeysFun;
    if (typeof Reflect !== "undefined" && Reflect.ownKeys) ownKeysFun = Reflect.ownKeys;
    else {
        var getOwnPropertySymbols = Object.getOwnPropertySymbols || ((o)=>[]);
        ownKeysFun = (o)=>Object.getOwnPropertyNames(o).concat(getOwnPropertySymbols(o));
    }
    return (o)=>{
        var ownKeys = ownKeysFun(o);
        var descriptors = {};
        var len = ownKeys.length | 0;
        var i = 0;
        while(i !== len){
            var key = ownKeys[i];
            Object.defineProperty(descriptors, key, {
                "configurable": true,
                "enumerable": true,
                "writable": true,
                "value": Object.getOwnPropertyDescriptor(o, key)
            });
            i = i + 1 | 0;
        }
        return descriptors;
    };
})();
var $L0;
function $propertyName(arg0) {
    for(var prop in arg0)return prop;
}
function $Char(c) {
    this.c = c;
}
$Char.prototype.toString = function() {
    return String.fromCharCode(this.c);
};
function $valueDescription(arg0) {
    return typeof arg0 === "number" ? arg0 === 0 && 1 / arg0 < 0 ? "number(-0)" : "number(" + arg0 + ")" : arg0 instanceof $c_RTLong ? "long" : arg0 instanceof $Char ? "char" : !!(arg0 && arg0.$classData) ? arg0.$classData.name : typeof arg0;
}
function $throwClassCastException(arg0, arg1) {
    throw new $c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError(new $c_jl_ClassCastException($valueDescription(arg0) + " cannot be cast to " + arg1));
}
function $throwArrayCastException(arg0, arg1, arg2) {
    while(--arg2)arg1 = "[" + arg1;
    $throwClassCastException(arg0, arg1);
}
function $throwArrayIndexOutOfBoundsException(arg0) {
    throw new $c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError(new $c_jl_ArrayIndexOutOfBoundsException(arg0 === null ? null : "" + arg0));
}
function $throwArrayStoreException(arg0) {
    throw new $c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError(new $c_jl_ArrayStoreException(arg0 === null ? null : $valueDescription(arg0)));
}
function $throwNegativeArraySizeException() {
    throw new $c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError(new $c_jl_NegativeArraySizeException());
}
function $noIsInstance(arg0) {
    throw new TypeError("Cannot call isInstance() on a Class representing a JS trait/object");
}
function $newArrayObject(arg0, arg1) {
    return $newArrayObjectInternal(arg0, arg1, 0);
}
function $newArrayObjectInternal(arg0, arg1, arg2) {
    var result = new arg0.constr(arg1[arg2]);
    if (arg2 < arg1.length - 1) {
        var subArrayClassData = arg0.componentData;
        var subLengthIndex = arg2 + 1;
        var underlying = result.u;
        for(var i = 0; i < underlying.length; i++)underlying[i] = $newArrayObjectInternal(subArrayClassData, arg1, subLengthIndex);
    }
    return result;
}
function $objectClone(arg0) {
    return Object.create(Object.getPrototypeOf(arg0), $getOwnPropertyDescriptors(arg0));
}
function $objectOrArrayClone(arg0) {
    return arg0.$classData.isArrayClass ? arg0.clone__O() : $objectClone(arg0);
}
function $objectClassName(arg0) {
    switch(typeof arg0){
        case "string":
            return "java.lang.String";
        case "number":
            if ($isInt(arg0)) {
                if (arg0 << 24 >> 24 === arg0) return "java.lang.Byte";
                else if (arg0 << 16 >> 16 === arg0) return "java.lang.Short";
                else return "java.lang.Integer";
            } else if ($isFloat(arg0)) return "java.lang.Float";
            else return "java.lang.Double";
        case "boolean":
            return "java.lang.Boolean";
        case "undefined":
            return "java.lang.Void";
        default:
            if (arg0 === null) return arg0.getClass__jl_Class();
            else if (arg0 instanceof $c_RTLong) return "java.lang.Long";
            else if (arg0 instanceof $Char) return "java.lang.Character";
            else if (!!(arg0 && arg0.$classData)) return arg0.$classData.name;
            else return null.getName__T();
    }
}
function $dp_hashCode__I(instance) {
    switch(typeof instance){
        case "string":
            return $f_T__hashCode__I(instance);
        case "number":
            return $f_jl_Double__hashCode__I(instance);
        case "boolean":
            return $f_jl_Boolean__hashCode__I(instance);
        case "undefined":
            return $f_jl_Void__hashCode__I(instance);
        default:
            if (!!(instance && instance.$classData) || instance === null) return instance.hashCode__I();
            else if (instance instanceof $c_RTLong) return $f_jl_Long__hashCode__I(instance);
            else if (instance instanceof $Char) return $f_jl_Character__hashCode__I($uC(instance));
            else return $c_O.prototype.hashCode__I.call(instance);
    }
}
function $dp_toString__T(instance) {
    return instance === void 0 ? "undefined" : instance.toString();
}
function $intDiv(arg0, arg1) {
    if (arg1 === 0) throw new $c_jl_ArithmeticException("/ by zero");
    else return arg0 / arg1 | 0;
}
function $intMod(arg0, arg1) {
    if (arg1 === 0) throw new $c_jl_ArithmeticException("/ by zero");
    else return arg0 % arg1 | 0;
}
function $doubleToInt(arg0) {
    return arg0 > 2147483647 ? 2147483647 : arg0 < -2147483648 ? -2147483648 : arg0 | 0;
}
function $charAt(arg0, arg1) {
    var r = arg0.charCodeAt(arg1);
    if (r !== r) throw new $c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError(new $c_jl_StringIndexOutOfBoundsException(arg1));
    else return r;
}
function $resolveSuperRef(arg0, arg1) {
    var getPrototypeOf = Object.getPrototyeOf;
    var getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;
    var superProto = arg0.prototype;
    while(superProto !== null){
        var desc = getOwnPropertyDescriptor(superProto, arg1);
        if (desc !== void 0) return desc;
        superProto = getPrototypeOf(superProto);
    }
}
function $superGet(arg0, arg1, arg2) {
    var desc = $resolveSuperRef(arg0, arg2);
    if (desc !== void 0) {
        var getter = desc.get;
        return getter !== void 0 ? getter.call(arg1) : getter.value;
    }
}
function $superSet(arg0, arg1, arg2, arg3) {
    var desc = $resolveSuperRef(arg0, arg2);
    if (desc !== void 0) {
        var setter = desc.set;
        if (setter !== void 0) {
            setter.call(arg1, arg3);
            return void 0;
        }
    }
    throw new TypeError("super has no setter '" + arg2 + "'.");
}
function $moduleDefault(arg0) {
    return arg0 && typeof arg0 === "object" && "default" in arg0 ? arg0["default"] : arg0;
}
function $arraycopyCheckBounds(arg0, arg1, arg2, arg3, arg4) {
    if (arg1 < 0 || arg3 < 0 || arg4 < 0 || arg1 > (arg0 - arg4 | 0) || arg3 > (arg2 - arg4 | 0)) $throwArrayIndexOutOfBoundsException(null);
}
function $arraycopyGeneric(arg0, arg1, arg2, arg3, arg4) {
    $arraycopyCheckBounds(arg0.length, arg1, arg2.length, arg3, arg4);
    if (arg0 !== arg2 || arg3 < arg1 || (arg1 + arg4 | 0) < arg3) for(var i = 0; i < arg4; i = i + 1 | 0)arg2[arg3 + i | 0] = arg0[arg1 + i | 0];
    else for(var i = arg4 - 1 | 0; i >= 0; i = i - 1 | 0)arg2[arg3 + i | 0] = arg0[arg1 + i | 0];
}
function $systemArraycopyRefs(arg0, arg1, arg2, arg3, arg4) {
    if (arg2.$classData.isAssignableFrom(arg0.$classData)) $arraycopyGeneric(arg0.u, arg1, arg2.u, arg3, arg4);
    else {
        var srcArray = arg0.u;
        $arraycopyCheckBounds(srcArray.length, arg1, arg2.u.length, arg3, arg4);
        for(var i = 0; i < arg4; i = i + 1 | 0)arg2.set(arg3 + i | 0, srcArray[arg1 + i | 0]);
    }
}
function $systemArraycopyFull(arg0, arg1, arg2, arg3, arg4) {
    var srcData = arg0 && arg0.$classData;
    if (srcData === (arg2 && arg2.$classData)) {
        if (srcData && srcData.isArrayClass) arg0.copyTo(arg1, arg2, arg3, arg4);
        else $throwArrayStoreException(null);
    } else if (arg0 instanceof $ac_O && arg2 instanceof $ac_O) $systemArraycopyRefs(arg0, arg1, arg2, arg3, arg4);
    else $throwArrayStoreException(null);
}
var $lastIDHash = 0;
var $idHashCodeMap = new WeakMap();
function $systemIdentityHashCode(obj) {
    switch(typeof obj){
        case "string":
            return $f_T__hashCode__I(obj);
        case "number":
            return $f_jl_Double__hashCode__I(obj);
        case "bigint":
            var biHash = 0;
            if (obj < BigInt(0)) obj = ~obj;
            while(obj !== BigInt(0)){
                biHash = biHash ^ Number(BigInt.asIntN(32, obj));
                obj = obj >> BigInt(32);
            }
            return biHash;
        case "boolean":
            return obj ? 1231 : 1237;
        case "undefined":
            return 0;
        case "symbol":
            var description = obj.description;
            return description === void 0 ? 0 : $f_T__hashCode__I(description);
        default:
            if (obj === null) return 0;
            else {
                var hash = $idHashCodeMap.get(obj);
                if (hash === void 0) {
                    hash = $lastIDHash + 1 | 0;
                    $lastIDHash = hash;
                    $idHashCodeMap.set(obj, hash);
                }
                return hash;
            }
    }
}
function $isByte(arg0) {
    return typeof arg0 === "number" && arg0 << 24 >> 24 === arg0 && 1 / arg0 !== 1 / -0;
}
function $isShort(arg0) {
    return typeof arg0 === "number" && arg0 << 16 >> 16 === arg0 && 1 / arg0 !== 1 / -0;
}
function $isInt(arg0) {
    return typeof arg0 === "number" && (arg0 | 0) === arg0 && 1 / arg0 !== 1 / -0;
}
function $isFloat(arg0) {
    return typeof arg0 === "number" && (arg0 !== arg0 || Math.fround(arg0) === arg0);
}
function $bC(arg0) {
    return new $Char(arg0);
}
var $bC0 = $bC(0);
function $uV(arg0) {
    return arg0 === void 0 || arg0 === null ? void 0 : $throwClassCastException(arg0, "java.lang.Void");
}
function $uZ(arg0) {
    return typeof arg0 === "boolean" || arg0 === null ? !!arg0 : $throwClassCastException(arg0, "java.lang.Boolean");
}
function $uC(arg0) {
    return arg0 instanceof $Char || arg0 === null ? arg0 === null ? 0 : arg0.c : $throwClassCastException(arg0, "java.lang.Character");
}
function $uB(arg0) {
    return $isByte(arg0) || arg0 === null ? arg0 | 0 : $throwClassCastException(arg0, "java.lang.Byte");
}
function $uS(arg0) {
    return $isShort(arg0) || arg0 === null ? arg0 | 0 : $throwClassCastException(arg0, "java.lang.Short");
}
function $uI(arg0) {
    return $isInt(arg0) || arg0 === null ? arg0 | 0 : $throwClassCastException(arg0, "java.lang.Integer");
}
function $uJ(arg0) {
    return arg0 instanceof $c_RTLong || arg0 === null ? arg0 === null ? $L0 : arg0 : $throwClassCastException(arg0, "java.lang.Long");
}
function $uF(arg0) {
    return $isFloat(arg0) || arg0 === null ? +arg0 : $throwClassCastException(arg0, "java.lang.Float");
}
function $uD(arg0) {
    return typeof arg0 === "number" || arg0 === null ? +arg0 : $throwClassCastException(arg0, "java.lang.Double");
}
function $uT(arg0) {
    return typeof arg0 === "string" || arg0 === null ? arg0 === null ? "" : arg0 : $throwClassCastException(arg0, "java.lang.String");
}
/** @constructor */ function $c_O() {
/*<skip>*/ }
$c_O.prototype.constructor = $c_O;
/** @constructor */ function $h_O() {
/*<skip>*/ }
$h_O.prototype = $c_O.prototype;
$c_O.prototype.hashCode__I = function() {
    return $systemIdentityHashCode(this);
};
$c_O.prototype.toString__T = function() {
    var i = this.hashCode__I();
    return $objectClassName(this) + "@" + $as_T($uD(i >>> 0.0).toString(16));
};
$c_O.prototype.toString = function() {
    return this.toString__T();
};
function $ac_O(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Array(arg);
        for(var i = 0; i < arg; i++)this.u[i] = null;
    } else this.u = arg;
}
$ac_O.prototype = new $h_O();
$ac_O.prototype.constructor = $ac_O;
$ac_O.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_O.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_O.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyGeneric(this.u, srcPos, dest.u, destPos, length);
};
$ac_O.prototype.clone__O = function() {
    return new $ac_O(this.u.slice());
};
function $ah_O() {
/*<skip>*/ }
$ah_O.prototype = $ac_O.prototype;
function $ac_Z(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Array(arg);
        for(var i = 0; i < arg; i++)this.u[i] = false;
    } else this.u = arg;
}
$ac_Z.prototype = new $h_O();
$ac_Z.prototype.constructor = $ac_Z;
$ac_Z.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_Z.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_Z.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyGeneric(this.u, srcPos, dest.u, destPos, length);
};
$ac_Z.prototype.clone__O = function() {
    return new $ac_Z(this.u.slice());
};
function $ac_C(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Uint16Array(arg);
    } else this.u = arg;
}
$ac_C.prototype = new $h_O();
$ac_C.prototype.constructor = $ac_C;
$ac_C.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_C.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_C.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyCheckBounds(this.u.length, srcPos, dest.u.length, destPos, length);
    dest.u.set(this.u.subarray(srcPos, srcPos + length | 0), destPos);
};
$ac_C.prototype.clone__O = function() {
    return new $ac_C(this.u.slice());
};
function $ac_B(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Int8Array(arg);
    } else this.u = arg;
}
$ac_B.prototype = new $h_O();
$ac_B.prototype.constructor = $ac_B;
$ac_B.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_B.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_B.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyCheckBounds(this.u.length, srcPos, dest.u.length, destPos, length);
    dest.u.set(this.u.subarray(srcPos, srcPos + length | 0), destPos);
};
$ac_B.prototype.clone__O = function() {
    return new $ac_B(this.u.slice());
};
function $ac_S(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Int16Array(arg);
    } else this.u = arg;
}
$ac_S.prototype = new $h_O();
$ac_S.prototype.constructor = $ac_S;
$ac_S.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_S.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_S.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyCheckBounds(this.u.length, srcPos, dest.u.length, destPos, length);
    dest.u.set(this.u.subarray(srcPos, srcPos + length | 0), destPos);
};
$ac_S.prototype.clone__O = function() {
    return new $ac_S(this.u.slice());
};
function $ac_I(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Int32Array(arg);
    } else this.u = arg;
}
$ac_I.prototype = new $h_O();
$ac_I.prototype.constructor = $ac_I;
$ac_I.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_I.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_I.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyCheckBounds(this.u.length, srcPos, dest.u.length, destPos, length);
    dest.u.set(this.u.subarray(srcPos, srcPos + length | 0), destPos);
};
$ac_I.prototype.clone__O = function() {
    return new $ac_I(this.u.slice());
};
function $ac_J(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Array(arg);
        for(var i = 0; i < arg; i++)this.u[i] = $L0;
    } else this.u = arg;
}
$ac_J.prototype = new $h_O();
$ac_J.prototype.constructor = $ac_J;
$ac_J.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_J.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_J.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyGeneric(this.u, srcPos, dest.u, destPos, length);
};
$ac_J.prototype.clone__O = function() {
    return new $ac_J(this.u.slice());
};
function $ac_F(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Float32Array(arg);
    } else this.u = arg;
}
$ac_F.prototype = new $h_O();
$ac_F.prototype.constructor = $ac_F;
$ac_F.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_F.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_F.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyCheckBounds(this.u.length, srcPos, dest.u.length, destPos, length);
    dest.u.set(this.u.subarray(srcPos, srcPos + length | 0), destPos);
};
$ac_F.prototype.clone__O = function() {
    return new $ac_F(this.u.slice());
};
function $ac_D(arg) {
    if (typeof arg === "number") {
        if (arg < 0) $throwNegativeArraySizeException();
        this.u = new Float64Array(arg);
    } else this.u = arg;
}
$ac_D.prototype = new $h_O();
$ac_D.prototype.constructor = $ac_D;
$ac_D.prototype.get = function(i) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    return this.u[i];
};
$ac_D.prototype.set = function(i, v) {
    if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
    this.u[i] = v;
};
$ac_D.prototype.copyTo = function(srcPos, dest, destPos, length) {
    $arraycopyCheckBounds(this.u.length, srcPos, dest.u.length, destPos, length);
    dest.u.set(this.u.subarray(srcPos, srcPos + length | 0), destPos);
};
$ac_D.prototype.clone__O = function() {
    return new $ac_D(this.u.slice());
};
function $TypeData() {
    this.constr = void 0;
    this.ancestors = null;
    this.componentData = null;
    this.arrayBase = null;
    this.arrayDepth = 0;
    this.zero = null;
    this.arrayEncodedName = "";
    this._classOf = void 0;
    this._arrayOf = void 0;
    this.isAssignableFromFun = void 0;
    this.wrapArray = void 0;
    this.isJSType = false;
    this.name = "";
    this.isPrimitive = false;
    this.isInterface = false;
    this.isArrayClass = false;
    this.isInstance = void 0;
}
$TypeData.prototype.initPrim = function(zero, arrayEncodedName, displayName, arrayClass, typedArrayClass) {
    this.ancestors = {};
    this.zero = zero;
    this.arrayEncodedName = arrayEncodedName;
    var self = this;
    this.isAssignableFromFun = (that)=>that === self;
    this.name = displayName;
    this.isPrimitive = true;
    this.isInstance = (obj)=>false;
    if (arrayClass !== void 0) this._arrayOf = new $TypeData().initSpecializedArray(this, arrayClass, typedArrayClass);
    return this;
};
$TypeData.prototype.initClass = function(internalNameObj, isInterface, fullName, ancestors, isJSType, parentData, isInstance) {
    var internalName = $propertyName(internalNameObj);
    this.ancestors = ancestors;
    this.arrayEncodedName = "L" + fullName + ";";
    this.isAssignableFromFun = (that)=>!!that.ancestors[internalName];
    this.isJSType = !!isJSType;
    this.name = fullName;
    this.isInterface = isInterface;
    this.isInstance = isInstance || ((obj)=>!!(obj && obj.$classData && obj.$classData.ancestors[internalName]));
    return this;
};
$TypeData.prototype.initSpecializedArray = function(componentData, arrayClass, typedArrayClass, isAssignableFromFun) {
    arrayClass.prototype.$classData = this;
    var name = "[" + componentData.arrayEncodedName;
    this.constr = arrayClass;
    this.ancestors = {
        O: 1,
        jl_Cloneable: 1,
        Ljava_io_Serializable: 1
    };
    this.componentData = componentData;
    this.arrayBase = componentData;
    this.arrayDepth = 1;
    this.arrayEncodedName = name;
    this.name = name;
    this.isArrayClass = true;
    var self = this;
    this.isAssignableFromFun = isAssignableFromFun || ((that)=>self === that);
    this.wrapArray = typedArrayClass ? (array)=>new arrayClass(new typedArrayClass(array)) : (array)=>new arrayClass(array);
    this.isInstance = (obj)=>obj instanceof arrayClass;
    return this;
};
$TypeData.prototype.initArray = function(componentData) {
    function ArrayClass(arg) {
        if (typeof arg === "number") {
            if (arg < 0) $throwNegativeArraySizeException();
            this.u = new Array(arg);
            for(var i = 0; i < arg; i++)this.u[i] = null;
        } else this.u = arg;
    }
    ArrayClass.prototype = new $ah_O();
    ArrayClass.prototype.constructor = ArrayClass;
    ArrayClass.prototype.set = function(i, v) {
        if (i < 0 || i >= this.u.length) $throwArrayIndexOutOfBoundsException(i);
        if (v !== null && !componentData.isJSType && !componentData.isInstance(v)) $throwArrayStoreException(v);
        this.u[i] = v;
    };
    ArrayClass.prototype.copyTo = function(srcPos, dest, destPos, length) {
        $arraycopyGeneric(this.u, srcPos, dest.u, destPos, length);
    };
    ArrayClass.prototype.clone__O = function() {
        return new ArrayClass(this.u.slice());
    };
    var arrayBase = componentData.arrayBase || componentData;
    var arrayDepth = componentData.arrayDepth + 1;
    ArrayClass.prototype.$classData = this;
    var name = "[" + componentData.arrayEncodedName;
    this.constr = ArrayClass;
    this.ancestors = {
        O: 1,
        jl_Cloneable: 1,
        Ljava_io_Serializable: 1
    };
    this.componentData = componentData;
    this.arrayBase = arrayBase;
    this.arrayDepth = arrayDepth;
    this.arrayEncodedName = name;
    this.name = name;
    this.isArrayClass = true;
    var isAssignableFromFun = (that)=>{
        var thatDepth = that.arrayDepth;
        return thatDepth === arrayDepth ? arrayBase.isAssignableFromFun(that.arrayBase) : thatDepth > arrayDepth && arrayBase === $d_O;
    };
    this.isAssignableFromFun = isAssignableFromFun;
    this.wrapArray = (array)=>new ArrayClass(array);
    var self = this;
    this.isInstance = (obj)=>{
        var data = obj && obj.$classData;
        return !!data && (data === self || isAssignableFromFun(data));
    };
    return this;
};
$TypeData.prototype.getArrayOf = function() {
    if (!this._arrayOf) this._arrayOf = new $TypeData().initArray(this);
    return this._arrayOf;
};
$TypeData.prototype.isAssignableFrom = function(that) {
    return this === that || this.isAssignableFromFun(that);
};
function $isArrayOf_O(obj, depth) {
    var data = obj && obj.$classData;
    if (!data) return false;
    else {
        var arrayDepth = data.arrayDepth;
        return arrayDepth === depth ? !data.arrayBase.isPrimitive : arrayDepth > depth;
    }
}
function $isArrayOf_Z(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_Z);
}
function $isArrayOf_C(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_C);
}
function $isArrayOf_B(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_B);
}
function $isArrayOf_S(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_S);
}
function $isArrayOf_I(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_I);
}
function $isArrayOf_J(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_J);
}
function $isArrayOf_F(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_F);
}
function $isArrayOf_D(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase === $d_D);
}
function $asArrayOf_O(obj, depth) {
    if ($isArrayOf_O(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "Ljava.lang.Object;", depth);
}
function $asArrayOf_Z(obj, depth) {
    if ($isArrayOf_Z(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "Z", depth);
}
function $asArrayOf_C(obj, depth) {
    if ($isArrayOf_C(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "C", depth);
}
function $asArrayOf_B(obj, depth) {
    if ($isArrayOf_B(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "B", depth);
}
function $asArrayOf_S(obj, depth) {
    if ($isArrayOf_S(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "S", depth);
}
function $asArrayOf_I(obj, depth) {
    if ($isArrayOf_I(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "I", depth);
}
function $asArrayOf_J(obj, depth) {
    if ($isArrayOf_J(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "J", depth);
}
function $asArrayOf_F(obj, depth) {
    if ($isArrayOf_F(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "F", depth);
}
function $asArrayOf_D(obj, depth) {
    if ($isArrayOf_D(obj, depth) || obj === null) return obj;
    else $throwArrayCastException(obj, "D", depth);
}
var $d_O = new $TypeData();
$d_O.ancestors = {
    O: 1
};
$d_O.arrayEncodedName = "Ljava.lang.Object;";
$d_O.isAssignableFromFun = (that)=>!that.isPrimitive;
$d_O.name = "java.lang.Object";
$d_O.isInstance = (obj)=>obj !== null;
$d_O._arrayOf = new $TypeData().initSpecializedArray($d_O, $ac_O, void 0, (that)=>{
    var thatDepth = that.arrayDepth;
    return thatDepth === 1 ? !that.arrayBase.isPrimitive : thatDepth > 1;
});
$c_O.prototype.$classData = $d_O;
var $d_V = new $TypeData().initPrim(void 0, "V", "void", void 0, void 0);
var $d_Z = new $TypeData().initPrim(false, "Z", "boolean", $ac_Z, void 0);
var $d_C = new $TypeData().initPrim(0, "C", "char", $ac_C, Uint16Array);
var $d_B = new $TypeData().initPrim(0, "B", "byte", $ac_B, Int8Array);
var $d_S = new $TypeData().initPrim(0, "S", "short", $ac_S, Int16Array);
var $d_I = new $TypeData().initPrim(0, "I", "int", $ac_I, Int32Array);
var $d_J = new $TypeData().initPrim(null, "J", "long", $ac_J, void 0);
var $d_F = new $TypeData().initPrim(0.0, "F", "float", $ac_F, Float32Array);
var $d_D = new $TypeData().initPrim(0.0, "D", "double", $ac_D, Float64Array);
/** @constructor */ function $c_Linfo_antoniojimenez_jobsboard_App() {
/*<skip>*/ }
$c_Linfo_antoniojimenez_jobsboard_App.prototype = new $h_O();
$c_Linfo_antoniojimenez_jobsboard_App.prototype.constructor = $c_Linfo_antoniojimenez_jobsboard_App;
/** @constructor */ function $h_Linfo_antoniojimenez_jobsboard_App() {
/*<skip>*/ }
$h_Linfo_antoniojimenez_jobsboard_App.prototype = $c_Linfo_antoniojimenez_jobsboard_App.prototype;
$c_Linfo_antoniojimenez_jobsboard_App.prototype.doSomething__T__V = function(containerId) {
    document.getElementById(containerId).innerHTML = "THIS ROCKS";
};
$c_Linfo_antoniojimenez_jobsboard_App.prototype.$js$exported$meth$doSomething__T__O = function(containerId) {
    this.doSomething__T__V(containerId);
};
$c_Linfo_antoniojimenez_jobsboard_App.prototype.doSomething = function(arg) {
    var prep0 = $as_T(arg);
    return this.$js$exported$meth$doSomething__T__O(prep0);
};
var $d_Linfo_antoniojimenez_jobsboard_App = new $TypeData().initClass({
    Linfo_antoniojimenez_jobsboard_App: 0
}, false, "info.antoniojimenez.jobsboard.App", {
    Linfo_antoniojimenez_jobsboard_App: 1,
    O: 1
});
$c_Linfo_antoniojimenez_jobsboard_App.prototype.$classData = $d_Linfo_antoniojimenez_jobsboard_App;
/** @constructor */ function $c_jl_FloatingPointBits$() {
    this.jl_FloatingPointBits$__f_java$lang$FloatingPointBits$$_areTypedArraysSupported = false;
    this.jl_FloatingPointBits$__f_arrayBuffer = null;
    this.jl_FloatingPointBits$__f_int32Array = null;
    this.jl_FloatingPointBits$__f_float64Array = null;
    this.jl_FloatingPointBits$__f_areTypedArraysBigEndian = false;
    this.jl_FloatingPointBits$__f_java$lang$FloatingPointBits$$doublePowsOf2 = null;
    $n_jl_FloatingPointBits$ = this;
    this.jl_FloatingPointBits$__f_java$lang$FloatingPointBits$$_areTypedArraysSupported = true;
    this.jl_FloatingPointBits$__f_arrayBuffer = new ArrayBuffer(8);
    this.jl_FloatingPointBits$__f_int32Array = new Int32Array(this.jl_FloatingPointBits$__f_arrayBuffer, 0, 2);
    new Float32Array(this.jl_FloatingPointBits$__f_arrayBuffer, 0, 2);
    this.jl_FloatingPointBits$__f_float64Array = new Float64Array(this.jl_FloatingPointBits$__f_arrayBuffer, 0, 1);
    this.jl_FloatingPointBits$__f_int32Array[0] = 16909060;
    this.jl_FloatingPointBits$__f_areTypedArraysBigEndian = $uB(new Int8Array(this.jl_FloatingPointBits$__f_arrayBuffer, 0, 8)[0]) === 1;
    this.jl_FloatingPointBits$__f_java$lang$FloatingPointBits$$doublePowsOf2 = null;
}
$c_jl_FloatingPointBits$.prototype = new $h_O();
$c_jl_FloatingPointBits$.prototype.constructor = $c_jl_FloatingPointBits$;
/** @constructor */ function $h_jl_FloatingPointBits$() {
/*<skip>*/ }
$h_jl_FloatingPointBits$.prototype = $c_jl_FloatingPointBits$.prototype;
$c_jl_FloatingPointBits$.prototype.numberHashCode__D__I = function(value) {
    var iv = $uI(value | 0.0);
    if (iv === value && 1.0 / value !== -Infinity) return iv;
    else {
        this.jl_FloatingPointBits$__f_float64Array[0] = value;
        return $uI(this.jl_FloatingPointBits$__f_int32Array[0]) ^ $uI(this.jl_FloatingPointBits$__f_int32Array[1]);
    }
};
var $d_jl_FloatingPointBits$ = new $TypeData().initClass({
    jl_FloatingPointBits$: 0
}, false, "java.lang.FloatingPointBits$", {
    jl_FloatingPointBits$: 1,
    O: 1
});
$c_jl_FloatingPointBits$.prototype.$classData = $d_jl_FloatingPointBits$;
var $n_jl_FloatingPointBits$;
function $m_jl_FloatingPointBits$() {
    if (!$n_jl_FloatingPointBits$) $n_jl_FloatingPointBits$ = new $c_jl_FloatingPointBits$();
    return $n_jl_FloatingPointBits$;
}
function $f_jl_Void__hashCode__I($thiz) {
    return 0;
}
function $f_jl_Void__toString__T($thiz) {
    return "undefined";
}
var $d_jl_Void = new $TypeData().initClass({
    jl_Void: 0
}, false, "java.lang.Void", {
    jl_Void: 1,
    O: 1
}, void 0, void 0, (x)=>x === void 0);
/** @constructor */ function $c_RTLong(lo, hi) {
    this.RTLong__f_lo = 0;
    this.RTLong__f_hi = 0;
    this.RTLong__f_lo = lo;
    this.RTLong__f_hi = hi;
}
$c_RTLong.prototype = new $h_O();
$c_RTLong.prototype.constructor = $c_RTLong;
/** @constructor */ function $h_RTLong() {
/*<skip>*/ }
$h_RTLong.prototype = $c_RTLong.prototype;
$c_RTLong.prototype.equals__O__Z = function(that) {
    if (that instanceof $c_RTLong) {
        var x2 = $as_RTLong(that);
        return this.RTLong__f_lo === x2.RTLong__f_lo && this.RTLong__f_hi === x2.RTLong__f_hi;
    } else return false;
};
$c_RTLong.prototype.hashCode__I = function() {
    return this.RTLong__f_lo ^ this.RTLong__f_hi;
};
$c_RTLong.prototype.toString__T = function() {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$toString__I__I__T(this.RTLong__f_lo, this.RTLong__f_hi);
};
$c_RTLong.prototype.toInt__I = function() {
    return this.RTLong__f_lo;
};
$c_RTLong.prototype.toFloat__F = function() {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$toFloat__I__I__F(this.RTLong__f_lo, this.RTLong__f_hi);
};
$c_RTLong.prototype.toDouble__D = function() {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$toDouble__I__I__D(this.RTLong__f_lo, this.RTLong__f_hi);
};
$c_RTLong.prototype.byteValue__B = function() {
    return this.RTLong__f_lo << 24 >> 24;
};
$c_RTLong.prototype.shortValue__S = function() {
    return this.RTLong__f_lo << 16 >> 16;
};
$c_RTLong.prototype.intValue__I = function() {
    return this.RTLong__f_lo;
};
$c_RTLong.prototype.longValue__J = function() {
    return $uJ(this);
};
$c_RTLong.prototype.floatValue__F = function() {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$toFloat__I__I__F(this.RTLong__f_lo, this.RTLong__f_hi);
};
$c_RTLong.prototype.doubleValue__D = function() {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$toDouble__I__I__D(this.RTLong__f_lo, this.RTLong__f_hi);
};
$c_RTLong.prototype.compareTo__O__I = function(that) {
    var b = $as_RTLong(that);
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$compare__I__I__I__I__I(this.RTLong__f_lo, this.RTLong__f_hi, b.RTLong__f_lo, b.RTLong__f_hi);
};
$c_RTLong.prototype.compareTo__jl_Long__I = function(that) {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$compare__I__I__I__I__I(this.RTLong__f_lo, this.RTLong__f_hi, that.RTLong__f_lo, that.RTLong__f_hi);
};
$c_RTLong.prototype.equals__RTLong__Z = function(b) {
    return this.RTLong__f_lo === b.RTLong__f_lo && this.RTLong__f_hi === b.RTLong__f_hi;
};
$c_RTLong.prototype.notEquals__RTLong__Z = function(b) {
    return !(this.RTLong__f_lo === b.RTLong__f_lo && this.RTLong__f_hi === b.RTLong__f_hi);
};
$c_RTLong.prototype.$less__RTLong__Z = function(b) {
    var ahi = this.RTLong__f_hi;
    var bhi = b.RTLong__f_hi;
    return ahi === bhi ? (-2147483648 ^ this.RTLong__f_lo) < (-2147483648 ^ b.RTLong__f_lo) : ahi < bhi;
};
$c_RTLong.prototype.$less$eq__RTLong__Z = function(b) {
    var ahi = this.RTLong__f_hi;
    var bhi = b.RTLong__f_hi;
    return ahi === bhi ? (-2147483648 ^ this.RTLong__f_lo) <= (-2147483648 ^ b.RTLong__f_lo) : ahi < bhi;
};
$c_RTLong.prototype.$greater__RTLong__Z = function(b) {
    var ahi = this.RTLong__f_hi;
    var bhi = b.RTLong__f_hi;
    return ahi === bhi ? (-2147483648 ^ this.RTLong__f_lo) > (-2147483648 ^ b.RTLong__f_lo) : ahi > bhi;
};
$c_RTLong.prototype.$greater$eq__RTLong__Z = function(b) {
    var ahi = this.RTLong__f_hi;
    var bhi = b.RTLong__f_hi;
    return ahi === bhi ? (-2147483648 ^ this.RTLong__f_lo) >= (-2147483648 ^ b.RTLong__f_lo) : ahi > bhi;
};
$c_RTLong.prototype.unary_$tilde__RTLong = function() {
    return new $c_RTLong(~this.RTLong__f_lo, ~this.RTLong__f_hi);
};
$c_RTLong.prototype.$bar__RTLong__RTLong = function(b) {
    return new $c_RTLong(this.RTLong__f_lo | b.RTLong__f_lo, this.RTLong__f_hi | b.RTLong__f_hi);
};
$c_RTLong.prototype.$amp__RTLong__RTLong = function(b) {
    return new $c_RTLong(this.RTLong__f_lo & b.RTLong__f_lo, this.RTLong__f_hi & b.RTLong__f_hi);
};
$c_RTLong.prototype.$up__RTLong__RTLong = function(b) {
    return new $c_RTLong(this.RTLong__f_lo ^ b.RTLong__f_lo, this.RTLong__f_hi ^ b.RTLong__f_hi);
};
$c_RTLong.prototype.$less$less__I__RTLong = function(n) {
    var lo = this.RTLong__f_lo;
    return new $c_RTLong((32 & n) === 0 ? lo << n : 0, (32 & n) === 0 ? (lo >>> 1 | 0) >>> (31 - n | 0) | 0 | this.RTLong__f_hi << n : lo << n);
};
$c_RTLong.prototype.$greater$greater$greater__I__RTLong = function(n) {
    var hi = this.RTLong__f_hi;
    return new $c_RTLong((32 & n) === 0 ? this.RTLong__f_lo >>> n | 0 | hi << 1 << (31 - n | 0) : hi >>> n | 0, (32 & n) === 0 ? hi >>> n | 0 : 0);
};
$c_RTLong.prototype.$greater$greater__I__RTLong = function(n) {
    var hi = this.RTLong__f_hi;
    return new $c_RTLong((32 & n) === 0 ? this.RTLong__f_lo >>> n | 0 | hi << 1 << (31 - n | 0) : hi >> n, (32 & n) === 0 ? hi >> n : hi >> 31);
};
$c_RTLong.prototype.unary_$minus__RTLong = function() {
    var lo = this.RTLong__f_lo;
    var hi = this.RTLong__f_hi;
    return new $c_RTLong(-lo | 0, lo !== 0 ? ~hi : -hi | 0);
};
$c_RTLong.prototype.$plus__RTLong__RTLong = function(b) {
    var alo = this.RTLong__f_lo;
    var ahi = this.RTLong__f_hi;
    var bhi = b.RTLong__f_hi;
    var lo = alo + b.RTLong__f_lo | 0;
    return new $c_RTLong(lo, (-2147483648 ^ lo) < (-2147483648 ^ alo) ? 1 + (ahi + bhi | 0) | 0 : ahi + bhi | 0);
};
$c_RTLong.prototype.$minus__RTLong__RTLong = function(b) {
    var alo = this.RTLong__f_lo;
    var ahi = this.RTLong__f_hi;
    var bhi = b.RTLong__f_hi;
    var lo = alo - b.RTLong__f_lo | 0;
    return new $c_RTLong(lo, (-2147483648 ^ lo) > (-2147483648 ^ alo) ? -1 + (ahi - bhi | 0) | 0 : ahi - bhi | 0);
};
$c_RTLong.prototype.$times__RTLong__RTLong = function(b) {
    var alo = this.RTLong__f_lo;
    var blo = b.RTLong__f_lo;
    var a0 = 65535 & alo;
    var a1 = alo >>> 16 | 0;
    var b0 = 65535 & blo;
    var b1 = blo >>> 16 | 0;
    var a0b0 = Math.imul(a0, b0);
    var a1b0 = Math.imul(a1, b0);
    var a0b1 = Math.imul(a0, b1);
    var lo = a0b0 + ((a1b0 + a0b1 | 0) << 16) | 0;
    var c1part = (a0b0 >>> 16 | 0) + a0b1 | 0;
    var hi = (((Math.imul(alo, b.RTLong__f_hi) + Math.imul(this.RTLong__f_hi, blo) | 0) + Math.imul(a1, b1) | 0) + (c1part >>> 16 | 0) | 0) + (((65535 & c1part) + a1b0 | 0) >>> 16 | 0) | 0;
    return new $c_RTLong(lo, hi);
};
$c_RTLong.prototype.$div__RTLong__RTLong = function(b) {
    var this$1 = $m_RTLong$();
    var lo = this$1.divideImpl__I__I__I__I__I(this.RTLong__f_lo, this.RTLong__f_hi, b.RTLong__f_lo, b.RTLong__f_hi);
    return new $c_RTLong(lo, this$1.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn);
};
$c_RTLong.prototype.$percent__RTLong__RTLong = function(b) {
    var this$1 = $m_RTLong$();
    var lo = this$1.remainderImpl__I__I__I__I__I(this.RTLong__f_lo, this.RTLong__f_hi, b.RTLong__f_lo, b.RTLong__f_hi);
    return new $c_RTLong(lo, this$1.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn);
};
function $as_RTLong(obj) {
    return obj instanceof $c_RTLong || obj === null ? obj : $throwClassCastException(obj, "org.scalajs.linker.runtime.RuntimeLong");
}
function $isArrayOf_RTLong(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase.ancestors.RTLong);
}
function $asArrayOf_RTLong(obj, depth) {
    return $isArrayOf_RTLong(obj, depth) || obj === null ? obj : $throwArrayCastException(obj, "Lorg.scalajs.linker.runtime.RuntimeLong;", depth);
}
var $d_RTLong = new $TypeData().initClass({
    RTLong: 0
}, false, "org.scalajs.linker.runtime.RuntimeLong", {
    RTLong: 1,
    O: 1
});
$c_RTLong.prototype.$classData = $d_RTLong;
function $p_RTLong$__toUnsignedString__I__I__T($thiz, lo, hi) {
    if ((-2097152 & hi) === 0) {
        var this$1 = 4.294967296E9 * hi + $uD(lo >>> 0.0);
        return "" + this$1;
    } else return $as_T($p_RTLong$__unsignedDivModHelper__I__I__I__I__I__O($thiz, lo, hi, 1000000000, 0, 2));
}
function $p_RTLong$__unsigned_$div__I__I__I__I__I($thiz, alo, ahi, blo, bhi) {
    if ((-2097152 & ahi) === 0) {
        if ((-2097152 & bhi) === 0) {
            var aDouble = 4.294967296E9 * ahi + $uD(alo >>> 0.0);
            var bDouble = 4.294967296E9 * bhi + $uD(blo >>> 0.0);
            var rDouble = aDouble / bDouble;
            var x = rDouble / 4.294967296E9;
            $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = $uI(x | 0.0);
            return $uI(rDouble | 0.0);
        } else {
            $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
            return 0;
        }
    } else if (bhi === 0 && (blo & (-1 + blo | 0)) === 0) {
        var pow = 31 - $uI(Math.clz32(blo)) | 0;
        $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = ahi >>> pow | 0;
        return alo >>> pow | 0 | ahi << 1 << (31 - pow | 0);
    } else if (blo === 0 && (bhi & (-1 + bhi | 0)) === 0) {
        var pow$2 = 31 - $uI(Math.clz32(bhi)) | 0;
        $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
        return ahi >>> pow$2 | 0;
    } else return $uI($p_RTLong$__unsignedDivModHelper__I__I__I__I__I__O($thiz, alo, ahi, blo, bhi, 0));
}
function $p_RTLong$__unsigned_$percent__I__I__I__I__I($thiz, alo, ahi, blo, bhi) {
    if ((-2097152 & ahi) === 0) {
        if ((-2097152 & bhi) === 0) {
            var aDouble = 4.294967296E9 * ahi + $uD(alo >>> 0.0);
            var bDouble = 4.294967296E9 * bhi + $uD(blo >>> 0.0);
            var rDouble = aDouble % bDouble;
            var x = rDouble / 4.294967296E9;
            $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = $uI(x | 0.0);
            return $uI(rDouble | 0.0);
        } else {
            $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = ahi;
            return alo;
        }
    } else if (bhi === 0 && (blo & (-1 + blo | 0)) === 0) {
        $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
        return alo & (-1 + blo | 0);
    } else if (blo === 0 && (bhi & (-1 + bhi | 0)) === 0) {
        $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = ahi & (-1 + bhi | 0);
        return alo;
    } else return $uI($p_RTLong$__unsignedDivModHelper__I__I__I__I__I__O($thiz, alo, ahi, blo, bhi, 1));
}
function $p_RTLong$__unsignedDivModHelper__I__I__I__I__I__O($thiz, alo, ahi, blo, bhi, ask) {
    var shift = (bhi !== 0 ? $uI(Math.clz32(bhi)) : 32 + $uI(Math.clz32(blo)) | 0) - (ahi !== 0 ? $uI(Math.clz32(ahi)) : 32 + $uI(Math.clz32(alo)) | 0) | 0;
    var n = shift;
    var lo = (32 & n) === 0 ? blo << n : 0;
    var hi = (32 & n) === 0 ? (blo >>> 1 | 0) >>> (31 - n | 0) | 0 | bhi << n : blo << n;
    var bShiftLo = lo;
    var bShiftHi = hi;
    var remLo = alo;
    var remHi = ahi;
    var quotLo = 0;
    var quotHi = 0;
    while(shift >= 0 && (-2097152 & remHi) !== 0){
        var alo$1 = remLo;
        var ahi$1 = remHi;
        var blo$1 = bShiftLo;
        var bhi$1 = bShiftHi;
        if (ahi$1 === bhi$1 ? (-2147483648 ^ alo$1) >= (-2147483648 ^ blo$1) : (-2147483648 ^ ahi$1) >= (-2147483648 ^ bhi$1)) {
            var lo$1 = remLo;
            var hi$1 = remHi;
            var lo$2 = bShiftLo;
            var hi$2 = bShiftHi;
            var lo$3 = lo$1 - lo$2 | 0;
            var hi$3 = (-2147483648 ^ lo$3) > (-2147483648 ^ lo$1) ? -1 + (hi$1 - hi$2 | 0) | 0 : hi$1 - hi$2 | 0;
            remLo = lo$3;
            remHi = hi$3;
            if (shift < 32) quotLo = quotLo | 1 << shift;
            else quotHi = quotHi | 1 << shift;
        }
        shift = -1 + shift | 0;
        var lo$4 = bShiftLo;
        var hi$4 = bShiftHi;
        var lo$5 = lo$4 >>> 1 | 0 | hi$4 << 31;
        var hi$5 = hi$4 >>> 1 | 0;
        bShiftLo = lo$5;
        bShiftHi = hi$5;
    }
    var alo$2 = remLo;
    var ahi$2 = remHi;
    if (ahi$2 === bhi ? (-2147483648 ^ alo$2) >= (-2147483648 ^ blo) : (-2147483648 ^ ahi$2) >= (-2147483648 ^ bhi)) {
        var lo$6 = remLo;
        var hi$6 = remHi;
        var remDouble = 4.294967296E9 * hi$6 + $uD(lo$6 >>> 0.0);
        var bDouble = 4.294967296E9 * bhi + $uD(blo >>> 0.0);
        if (ask !== 1) {
            var x = remDouble / bDouble;
            var lo$7 = $uI(x | 0.0);
            var x$1 = x / 4.294967296E9;
            var hi$7 = $uI(x$1 | 0.0);
            var lo$8 = quotLo;
            var hi$8 = quotHi;
            var lo$9 = lo$8 + lo$7 | 0;
            var hi$9 = (-2147483648 ^ lo$9) < (-2147483648 ^ lo$8) ? 1 + (hi$8 + hi$7 | 0) | 0 : hi$8 + hi$7 | 0;
            quotLo = lo$9;
            quotHi = hi$9;
        }
        if (ask !== 0) {
            var rem_mod_bDouble = remDouble % bDouble;
            remLo = $uI(rem_mod_bDouble | 0.0);
            var x$2 = rem_mod_bDouble / 4.294967296E9;
            remHi = $uI(x$2 | 0.0);
        }
    }
    if (ask === 0) {
        $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = quotHi;
        return quotLo;
    } else if (ask === 1) {
        $thiz.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = remHi;
        return remLo;
    } else {
        var lo$10 = quotLo;
        var hi$10 = quotHi;
        var quot = 4.294967296E9 * hi$10 + $uD(lo$10 >>> 0.0);
        var this$7 = remLo;
        var remStr = "" + this$7;
        var start = remStr.length;
        return "" + quot + $as_T("000000000".substring(start)) + remStr;
    }
}
/** @constructor */ function $c_RTLong$() {
    this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
}
$c_RTLong$.prototype = new $h_O();
$c_RTLong$.prototype.constructor = $c_RTLong$;
/** @constructor */ function $h_RTLong$() {
/*<skip>*/ }
$h_RTLong$.prototype = $c_RTLong$.prototype;
$c_RTLong$.prototype.org$scalajs$linker$runtime$RuntimeLong$$toString__I__I__T = function(lo, hi) {
    return hi === lo >> 31 ? "" + lo : hi < 0 ? "-" + $p_RTLong$__toUnsignedString__I__I__T(this, -lo | 0, lo !== 0 ? ~hi : -hi | 0) : $p_RTLong$__toUnsignedString__I__I__T(this, lo, hi);
};
$c_RTLong$.prototype.org$scalajs$linker$runtime$RuntimeLong$$toDouble__I__I__D = function(lo, hi) {
    if (hi < 0) {
        var x = lo !== 0 ? ~hi : -hi | 0;
        var $$x1 = $uD(x >>> 0.0);
        var x$1 = -lo | 0;
        return -(4.294967296E9 * $$x1 + $uD(x$1 >>> 0.0));
    } else return 4.294967296E9 * hi + $uD(lo >>> 0.0);
};
$c_RTLong$.prototype.org$scalajs$linker$runtime$RuntimeLong$$toFloat__I__I__F = function(lo, hi) {
    if (hi < 0) {
        var lo$1 = -lo | 0;
        var hi$1 = lo !== 0 ? ~hi : -hi | 0;
        var abs__lo = lo$1;
        var abs__hi = hi$1;
    } else {
        var abs__lo = lo;
        var abs__hi = hi;
    }
    var hi$2 = abs__hi;
    if ((-2097152 & hi$2) === 0 || (65535 & abs__lo) === 0) var compressedAbsLo = abs__lo;
    else var compressedAbsLo = 32768 | -65536 & abs__lo;
    var x = abs__hi;
    var absRes = 4.294967296E9 * $uD(x >>> 0.0) + $uD(compressedAbsLo >>> 0.0);
    return Math.fround(hi < 0 ? -absRes : absRes);
};
$c_RTLong$.prototype.fromInt__I__RTLong = function(value) {
    return new $c_RTLong(value, value >> 31);
};
$c_RTLong$.prototype.fromDouble__D__RTLong = function(value) {
    var lo = this.org$scalajs$linker$runtime$RuntimeLong$$fromDoubleImpl__D__I(value);
    return new $c_RTLong(lo, this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn);
};
$c_RTLong$.prototype.org$scalajs$linker$runtime$RuntimeLong$$fromDoubleImpl__D__I = function(value) {
    if (value < -9223372036854776000) {
        this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = -2147483648;
        return 0;
    } else if (value >= 9.223372036854776E18) {
        this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 2147483647;
        return -1;
    } else {
        var rawLo = $uI(value | 0.0);
        var x = value / 4.294967296E9;
        var rawHi = $uI(x | 0.0);
        this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = value < 0.0 && rawLo !== 0 ? -1 + rawHi | 0 : rawHi;
        return rawLo;
    }
};
$c_RTLong$.prototype.org$scalajs$linker$runtime$RuntimeLong$$compare__I__I__I__I__I = function(alo, ahi, blo, bhi) {
    return ahi === bhi ? alo === blo ? 0 : (-2147483648 ^ alo) < (-2147483648 ^ blo) ? -1 : 1 : ahi < bhi ? -1 : 1;
};
$c_RTLong$.prototype.divideImpl__I__I__I__I__I = function(alo, ahi, blo, bhi) {
    if ((blo | bhi) === 0) throw new $c_jl_ArithmeticException("/ by zero");
    if (ahi === alo >> 31) {
        if (bhi === blo >> 31) {
            if (alo === -2147483648 && blo === -1) {
                this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
                return -2147483648;
            } else {
                var lo = $intDiv(alo, blo);
                this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = lo >> 31;
                return lo;
            }
        } else if (alo === -2147483648 && blo === -2147483648 && bhi === 0) {
            this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = -1;
            return -1;
        } else {
            this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
            return 0;
        }
    } else {
        if (ahi < 0) {
            var lo$1 = -alo | 0;
            var hi = alo !== 0 ? ~ahi : -ahi | 0;
            var aAbs__lo = lo$1;
            var aAbs__hi = hi;
        } else {
            var aAbs__lo = alo;
            var aAbs__hi = ahi;
        }
        if (bhi < 0) {
            var lo$2 = -blo | 0;
            var hi$1 = blo !== 0 ? ~bhi : -bhi | 0;
            var bAbs__lo = lo$2;
            var bAbs__hi = hi$1;
        } else {
            var bAbs__lo = blo;
            var bAbs__hi = bhi;
        }
        var absRLo = $p_RTLong$__unsigned_$div__I__I__I__I__I(this, aAbs__lo, aAbs__hi, bAbs__lo, bAbs__hi);
        if ((ahi ^ bhi) >= 0) return absRLo;
        else {
            var hi$2 = this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn;
            this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = absRLo !== 0 ? ~hi$2 : -hi$2 | 0;
            return -absRLo | 0;
        }
    }
};
$c_RTLong$.prototype.remainderImpl__I__I__I__I__I = function(alo, ahi, blo, bhi) {
    if ((blo | bhi) === 0) throw new $c_jl_ArithmeticException("/ by zero");
    if (ahi === alo >> 31) {
        if (bhi === blo >> 31) {
            if (blo !== -1) {
                var lo = $intMod(alo, blo);
                this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = lo >> 31;
                return lo;
            } else {
                this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
                return 0;
            }
        } else if (alo === -2147483648 && blo === -2147483648 && bhi === 0) {
            this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = 0;
            return 0;
        } else {
            this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = ahi;
            return alo;
        }
    } else {
        if (ahi < 0) {
            var lo$1 = -alo | 0;
            var hi = alo !== 0 ? ~ahi : -ahi | 0;
            var aAbs__lo = lo$1;
            var aAbs__hi = hi;
        } else {
            var aAbs__lo = alo;
            var aAbs__hi = ahi;
        }
        if (bhi < 0) {
            var lo$2 = -blo | 0;
            var hi$1 = blo !== 0 ? ~bhi : -bhi | 0;
            var bAbs__lo = lo$2;
            var bAbs__hi = hi$1;
        } else {
            var bAbs__lo = blo;
            var bAbs__hi = bhi;
        }
        var absRLo = $p_RTLong$__unsigned_$percent__I__I__I__I__I(this, aAbs__lo, aAbs__hi, bAbs__lo, bAbs__hi);
        if (ahi < 0) {
            var hi$2 = this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn;
            this.RTLong$__f_org$scalajs$linker$runtime$RuntimeLong$$hiReturn = absRLo !== 0 ? ~hi$2 : -hi$2 | 0;
            return -absRLo | 0;
        } else return absRLo;
    }
};
var $d_RTLong$ = new $TypeData().initClass({
    RTLong$: 0
}, false, "org.scalajs.linker.runtime.RuntimeLong$", {
    RTLong$: 1,
    O: 1
});
$c_RTLong$.prototype.$classData = $d_RTLong$;
var $n_RTLong$;
function $m_RTLong$() {
    if (!$n_RTLong$) $n_RTLong$ = new $c_RTLong$();
    return $n_RTLong$;
}
/** @constructor */ function $c_jl_Number() {
/*<skip>*/ }
$c_jl_Number.prototype = new $h_O();
$c_jl_Number.prototype.constructor = $c_jl_Number;
/** @constructor */ function $h_jl_Number() {
/*<skip>*/ }
$h_jl_Number.prototype = $c_jl_Number.prototype;
function $ct_jl_Throwable__T__jl_Throwable__Z__Z__($thiz, s, e, enableSuppression, writableStackTrace) {
    $thiz.jl_Throwable__f_s = s;
    if (writableStackTrace) $thiz.fillInStackTrace__jl_Throwable();
    return $thiz;
}
class $c_jl_Throwable extends Error {
    constructor(){
        super();
        this.jl_Throwable__f_s = null;
    }
    fillInStackTrace__jl_Throwable() {
        var $$x1 = this;
        var reference = $$x1;
        var identifyingString = Object.prototype.toString.call(reference);
        if (identifyingString !== "[object Error]") {
            if (Error.captureStackTrace === void 0) new Error();
            else Error.captureStackTrace(this);
        }
        return this;
    }
    toString__T() {
        var className = $objectClassName(this);
        var message = this.jl_Throwable__f_s;
        return message === null ? className : className + ": " + message;
    }
    $js$exported$meth$toString__O() {
        return this.toString__T();
    }
    $js$exported$prop$name__O() {
        return $objectClassName(this);
    }
    $js$exported$prop$message__O() {
        var m = this.jl_Throwable__f_s;
        return m === null ? "" : m;
    }
    hashCode__I() {
        return $c_O.prototype.hashCode__I.call(this);
    }
    get "message"() {
        return this.$js$exported$prop$message__O();
    }
    get "name"() {
        return this.$js$exported$prop$name__O();
    }
    "toString"() {
        return this.$js$exported$meth$toString__O();
    }
}
class $c_jl_Error extends $c_jl_Throwable {
}
class $c_jl_Exception extends $c_jl_Throwable {
}
function $f_jl_Boolean__hashCode__I($thiz) {
    return $thiz ? 1231 : 1237;
}
function $f_jl_Boolean__toString__T($thiz) {
    return "" + $thiz;
}
var $d_jl_Boolean = new $TypeData().initClass({
    jl_Boolean: 0
}, false, "java.lang.Boolean", {
    jl_Boolean: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1
}, void 0, void 0, (x)=>typeof x === "boolean");
function $f_jl_Character__hashCode__I($thiz) {
    return $thiz;
}
function $f_jl_Character__toString__T($thiz) {
    return $as_T(String.fromCharCode($thiz));
}
var $d_jl_Character = new $TypeData().initClass({
    jl_Character: 0
}, false, "java.lang.Character", {
    jl_Character: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1
}, void 0, void 0, (x)=>x instanceof $Char);
class $c_jl_RuntimeException extends $c_jl_Exception {
}
class $c_jl_VirtualMachineError extends $c_jl_Error {
}
class $c_jl_ArithmeticException extends $c_jl_RuntimeException {
    constructor(s){
        super();
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, s, null, true, true);
    }
}
var $d_jl_ArithmeticException = new $TypeData().initClass({
    jl_ArithmeticException: 0
}, false, "java.lang.ArithmeticException", {
    jl_ArithmeticException: 1,
    jl_RuntimeException: 1,
    jl_Exception: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_jl_ArithmeticException.prototype.$classData = $d_jl_ArithmeticException;
class $c_jl_ArrayStoreException extends $c_jl_RuntimeException {
    constructor(s){
        super();
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, s, null, true, true);
    }
}
var $d_jl_ArrayStoreException = new $TypeData().initClass({
    jl_ArrayStoreException: 0
}, false, "java.lang.ArrayStoreException", {
    jl_ArrayStoreException: 1,
    jl_RuntimeException: 1,
    jl_Exception: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_jl_ArrayStoreException.prototype.$classData = $d_jl_ArrayStoreException;
function $f_jl_Byte__hashCode__I($thiz) {
    return $thiz;
}
function $f_jl_Byte__toString__T($thiz) {
    return "" + $thiz;
}
var $d_jl_Byte = new $TypeData().initClass({
    jl_Byte: 0
}, false, "java.lang.Byte", {
    jl_Byte: 1,
    jl_Number: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1
}, void 0, void 0, (x)=>$isByte(x));
class $c_jl_ClassCastException extends $c_jl_RuntimeException {
    constructor(s){
        super();
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, s, null, true, true);
    }
}
var $d_jl_ClassCastException = new $TypeData().initClass({
    jl_ClassCastException: 0
}, false, "java.lang.ClassCastException", {
    jl_ClassCastException: 1,
    jl_RuntimeException: 1,
    jl_Exception: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_jl_ClassCastException.prototype.$classData = $d_jl_ClassCastException;
class $c_jl_IndexOutOfBoundsException extends $c_jl_RuntimeException {
}
class $c_jl_NegativeArraySizeException extends $c_jl_RuntimeException {
    constructor(){
        super();
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, null, null, true, true);
    }
}
var $d_jl_NegativeArraySizeException = new $TypeData().initClass({
    jl_NegativeArraySizeException: 0
}, false, "java.lang.NegativeArraySizeException", {
    jl_NegativeArraySizeException: 1,
    jl_RuntimeException: 1,
    jl_Exception: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_jl_NegativeArraySizeException.prototype.$classData = $d_jl_NegativeArraySizeException;
function $f_jl_Short__hashCode__I($thiz) {
    return $thiz;
}
function $f_jl_Short__toString__T($thiz) {
    return "" + $thiz;
}
var $d_jl_Short = new $TypeData().initClass({
    jl_Short: 0
}, false, "java.lang.Short", {
    jl_Short: 1,
    jl_Number: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1
}, void 0, void 0, (x)=>$isShort(x));
class $c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError extends $c_jl_VirtualMachineError {
    constructor(cause){
        super();
        var message = cause === null ? null : cause.toString__T();
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, message, cause, true, true);
    }
}
var $d_Lorg_scalajs_linker_runtime_UndefinedBehaviorError = new $TypeData().initClass({
    Lorg_scalajs_linker_runtime_UndefinedBehaviorError: 0
}, false, "org.scalajs.linker.runtime.UndefinedBehaviorError", {
    Lorg_scalajs_linker_runtime_UndefinedBehaviorError: 1,
    jl_VirtualMachineError: 1,
    jl_Error: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_Lorg_scalajs_linker_runtime_UndefinedBehaviorError.prototype.$classData = $d_Lorg_scalajs_linker_runtime_UndefinedBehaviorError;
class $c_jl_ArrayIndexOutOfBoundsException extends $c_jl_IndexOutOfBoundsException {
    constructor(s){
        super();
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, s, null, true, true);
    }
}
var $d_jl_ArrayIndexOutOfBoundsException = new $TypeData().initClass({
    jl_ArrayIndexOutOfBoundsException: 0
}, false, "java.lang.ArrayIndexOutOfBoundsException", {
    jl_ArrayIndexOutOfBoundsException: 1,
    jl_IndexOutOfBoundsException: 1,
    jl_RuntimeException: 1,
    jl_Exception: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_jl_ArrayIndexOutOfBoundsException.prototype.$classData = $d_jl_ArrayIndexOutOfBoundsException;
function $f_jl_Double__hashCode__I($thiz) {
    return $m_jl_FloatingPointBits$().numberHashCode__D__I($thiz);
}
function $f_jl_Double__toString__T($thiz) {
    return "" + $thiz;
}
var $d_jl_Double = new $TypeData().initClass({
    jl_Double: 0
}, false, "java.lang.Double", {
    jl_Double: 1,
    jl_Number: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1,
    jl_constant_ConstantDesc: 1
}, void 0, void 0, (x)=>typeof x === "number");
function $f_jl_Float__hashCode__I($thiz) {
    return $m_jl_FloatingPointBits$().numberHashCode__D__I($thiz);
}
function $f_jl_Float__toString__T($thiz) {
    return "" + $thiz;
}
var $d_jl_Float = new $TypeData().initClass({
    jl_Float: 0
}, false, "java.lang.Float", {
    jl_Float: 1,
    jl_Number: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1,
    jl_constant_ConstantDesc: 1
}, void 0, void 0, (x)=>$isFloat(x));
function $f_jl_Integer__hashCode__I($thiz) {
    return $thiz;
}
function $f_jl_Integer__toString__T($thiz) {
    return "" + $thiz;
}
var $d_jl_Integer = new $TypeData().initClass({
    jl_Integer: 0
}, false, "java.lang.Integer", {
    jl_Integer: 1,
    jl_Number: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1,
    jl_constant_ConstantDesc: 1
}, void 0, void 0, (x)=>$isInt(x));
function $f_jl_Long__hashCode__I($thiz) {
    var $$x1 = $thiz.RTLong__f_lo;
    var hi = $thiz.RTLong__f_hi;
    return $$x1 ^ hi;
}
function $f_jl_Long__toString__T($thiz) {
    return $m_RTLong$().org$scalajs$linker$runtime$RuntimeLong$$toString__I__I__T($thiz.RTLong__f_lo, $thiz.RTLong__f_hi);
}
var $d_jl_Long = new $TypeData().initClass({
    jl_Long: 0
}, false, "java.lang.Long", {
    jl_Long: 1,
    jl_Number: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_constant_Constable: 1,
    jl_constant_ConstantDesc: 1
}, void 0, void 0, (x)=>x instanceof $c_RTLong);
function $f_T__hashCode__I($thiz) {
    var res = 0;
    var mul = 1;
    var i = -1 + $thiz.length | 0;
    while(i >= 0){
        var $$x1 = res;
        var index = i;
        res = $$x1 + Math.imul($charAt($thiz, index), mul) | 0;
        mul = Math.imul(31, mul);
        i = -1 + i | 0;
    }
    return res;
}
function $f_T__toString__T($thiz) {
    return $thiz;
}
function $as_T(obj) {
    return typeof obj === "string" || obj === null ? obj : $throwClassCastException(obj, "java.lang.String");
}
function $isArrayOf_T(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase.ancestors.T);
}
function $asArrayOf_T(obj, depth) {
    return $isArrayOf_T(obj, depth) || obj === null ? obj : $throwArrayCastException(obj, "Ljava.lang.String;", depth);
}
var $d_T = new $TypeData().initClass({
    T: 0
}, false, "java.lang.String", {
    T: 1,
    O: 1,
    Ljava_io_Serializable: 1,
    jl_Comparable: 1,
    jl_CharSequence: 1,
    jl_constant_Constable: 1,
    jl_constant_ConstantDesc: 1
}, void 0, void 0, (x)=>typeof x === "string");
class $c_jl_StringIndexOutOfBoundsException extends $c_jl_IndexOutOfBoundsException {
    constructor(index){
        super();
        var s = "String index out of range: " + index;
        $ct_jl_Throwable__T__jl_Throwable__Z__Z__(this, s, null, true, true);
    }
}
var $d_jl_StringIndexOutOfBoundsException = new $TypeData().initClass({
    jl_StringIndexOutOfBoundsException: 0
}, false, "java.lang.StringIndexOutOfBoundsException", {
    jl_StringIndexOutOfBoundsException: 1,
    jl_IndexOutOfBoundsException: 1,
    jl_RuntimeException: 1,
    jl_Exception: 1,
    jl_Throwable: 1,
    O: 1,
    Ljava_io_Serializable: 1
});
$c_jl_StringIndexOutOfBoundsException.prototype.$classData = $d_jl_StringIndexOutOfBoundsException;
function $as_sjs_js_JavaScriptException(obj) {
    return obj === null ? obj : $throwClassCastException(obj, "scala.scalajs.js.JavaScriptException");
}
function $isArrayOf_sjs_js_JavaScriptException(obj, depth) {
    return !!(obj && obj.$classData && obj.$classData.arrayDepth === depth && obj.$classData.arrayBase.ancestors.sjs_js_JavaScriptException);
}
function $asArrayOf_sjs_js_JavaScriptException(obj, depth) {
    return $isArrayOf_sjs_js_JavaScriptException(obj, depth) || obj === null ? obj : $throwArrayCastException(obj, "Lscala.scalajs.js.JavaScriptException;", depth);
}
$L0 = new $c_RTLong(0, 0);
$d_J.zero = $L0;
exports.antonioApp = function() {
    return new $c_Linfo_antoniojimenez_jobsboard_App();
};

},{}]},["hF8lp","igcvL"], "igcvL", "parcelRequire94c2")

//# sourceMappingURL=index.5baa4167.js.map
