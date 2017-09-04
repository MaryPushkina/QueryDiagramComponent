/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


jsPlumb.ready(function () {

    jsPlumb.connect({
        source: "item_left",
        target: "item_right",
        endpoint: "Blank"
    });

    jsPlumb.draggable($(".item"));
    jsPlumb.draggable($(".item"));
});