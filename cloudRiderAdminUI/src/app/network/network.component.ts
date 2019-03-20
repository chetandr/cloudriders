import { Component, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as vis from 'vis';
const _ = require("lodash");

type Icon = Array<{ code: string, color: string }>;

@Component({
  selector: 'network-component',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})

export class NetworkComponent implements AfterViewInit {
  @ViewChild('vis') element: ElementRef;
  network: vis.Network;
  nodes: any;
  edges: any;
  nodeData : any[];
  edgeData: any[];
  options: Icon = [
    {
      code: '\uf20e',
      color: '#ffbb33'
    },
    {
      code: '\uf2b0',
      color: '#81c784'
    },
    {
      code: '\uf1b2',
      color: '#ff4444'
    },
    {
      code: '\uf2ce',
      color: '#ffbb33'
    }
  ];

  constructor(private http: HttpClient) {
    this.http = http;
    this.nodeData = [];
    this.edgeData = [];
  }

  ngAfterViewInit() {

    var optionsFA = {
      height: 270 + 'px',
      groups: {
        consortium: {
          shape: 'icon',
          icon: {
            face: 'FontAwesome',
            code: this.options[0].code,
            size: 50,
            color: this.options[0].color
          }
        },
        org: {
          shape: 'icon',
          icon: {
            face: 'FontAwesome',
            code: this.options[1].code,
            size: 50,
            color: this.options[1].color
          }
        },
        peer: {
          shape: 'icon',
          icon: {
            face: 'FontAwesome',
            code: this.options[2].code,
            size: 50,
            color: this.options[2].color
          }
        },
        channel: {
          shape: 'icon',
          icon: {
            face: 'FontAwesome',
            code: this.options[3].code,
            size: 50,
            color: this.options[3].color
          }
        }
      }
    };
    this.nodes = new vis.DataSet();
    this.edges = new vis.DataSet();
    this.network = new vis.Network(this.element.nativeElement, {nodes: this.nodes, edges: this.edges}, optionsFA);

    this.http.get("http://localhost:3030/hyperverse/getnodes").subscribe(
      Data => {
        
        Data["data"].forEach(dt => {
          if(!this.nodeData.find(nd => nd.id === dt.id && nd.label === dt.label && nd.group === dt.group)) {
            this.nodeData.push(dt);
          }
        })
        Data["edges"].forEach(dt => {
          if(!this.edgeData.find(nd => nd.from === dt.from && nd.to === dt.to)) {
            this.edgeData.push(dt);
          }
        })
        this.edgeData.forEach(element => {
          this.edges.add(element)
        });
        this.nodeData.forEach(element => {
          this.nodes.add(element)
        });
      
      });

  }

}
