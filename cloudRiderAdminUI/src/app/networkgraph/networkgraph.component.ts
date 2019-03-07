import { Component, OnInit } from '@angular/core';
import { DataSet } from 'vis';

@Component({
  selector: 'app-graph-visualization',
  templateUrl: './graph-visualization.component.html',
  styleUrls: ['./graph-visualization.component.css']
})
export class GraphVisualizationComponent {
  graphData = {};

  constructor() { }

  ngAfterContentInit(){
    // create an array with nodes
    var nodes = new DataSet([
      {id: 1, label: 'Node 1'},
      {id: 2, label: 'Node 2'},
      {id: 3, label: 'Node 3'},
      {id: 4, label: 'Node 4'},
      {id: 5, label: 'Node 5'}
    ]);

    // create an array with edges
    var edges = new DataSet([
      {from: 1, to: 3},
      {from: 1, to: 2},
      {from: 2, to: 4},
      {from: 2, to: 5}
    ]);

    // provide the data in the vis format
    this.graphData["nodes"] = nodes;
    this.graphData["edges"] = edges;
  }
}