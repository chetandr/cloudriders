import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import 'nvd3';

declare let d3: any;
@Component({
  selector: 'network-graph-component',
  template: `
    <div>
      <nvd3 [options]="options" [data]="data"></nvd3>
    </div>
  `,
  // include original styles
  styleUrls: [
    '../../../node_modules/nvd3/build/nv.d3.css'
  ],
  encapsulation: ViewEncapsulation.None
})

export class NetworkGraphComponent implements OnInit {
  options;
  data;
  ngOnInit() {
    this.options = {
      chart: {
        type: 'forceDirectedGraph',
        height: 450,
        margin : {
          top: 20,
          right: 20,
          bottom: 50,
          left: 55
        },
        x: function(d){return d.label;},
        y: function(d){return d.value;},
        showValues: true,
        valueFormat: function(d){
          return d3.format(',.4f')(d);
        },
        duration: 500,
        xAxis: {
          axisLabel: 'X Axis'
        },
        yAxis: {
          axisLabel: 'Y Axis',
          axisLabelDistance: -10
        }
      }
    }
    this.data = {
        "nodes":[
            {"Peer":"MyPeer 1","Organisation":1},
            {"Peer":"MyPeer 2","Organisation":1},
            {"Peer":"MyPeer 3","Organisation":1},
            {"Peer":"MyPeer 1","Organisation":2},
            {"Peer":"MyPeer 2","Organisation":2},
            {"Peer":"MyPeer 3","Organisation":2},
            {"Peer":"MyPeer 1","Organisation":3},
            {"Peer":"MyPeer 2","Organisation":3},
            {"Peer":"MyPeer 3","Organisation":3},
            {"Peer":"MyPeer 1","Organisation":4},
            {"Peer":"MyPeer 2","Organisation":4},
            {"Peer":"MyPeer 3","Organisation":4},
            {"Peer":"MyPeer 1","Organisation":5},
            {"Peer":"MyPeer 2","Organisation":5},
            {"Peer":"MyPeer 3","Organisation":5},
            {"Peer":"MyPeer 1","Organisation":6},
            {"Peer":"MyPeer 2","Organisation":6},
            {"Peer":"MyPeer 3","Organisation":6},
            
            
        ],
        "links":[
            {"source":1,"target":2,"value":10},
        ]
    };
  }
}