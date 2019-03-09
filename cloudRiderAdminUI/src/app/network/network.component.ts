import { Component, ElementRef, ViewChild, AfterViewInit, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import { HttpClient } from '@angular/common/http';

import * as vis from 'vis';

type Icon = Array<{code: string, color: string}>;
type Node = Array<{id: number, label: string, group : string}>;

type Edge = Array<{from: number, to: number}>;

@Component({
  selector: 'network-component',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})

export class NetworkComponent implements AfterViewInit, OnInit {
  @ViewChild('vis') element: ElementRef;
  network: vis.Network;

  nodes1: Array<Node>

  visNodes: any

  edges1: Array<Edge>

  visEdges: any

  options: Icon = [
    {
      code : '\uf20e',
      color : '#ffbb33'
    },
    {
      code : '\uf2b0',
      color : '#81c784'
    },
    {
      code : '\uf1b2',
      color : '#ff4444'
    },
    {
      code : '\uf2ce',
      color : '#ffbb33'
    }
  ];

  constructor(private router : Router, private http: HttpClient) {
    this.http = http;
  }

  ngOnInit() {
    this.http.get("http://10.44.14.143:3000/hyperverse/getnodes").subscribe((data: Node[]) => {
      console.log(data);
      this.nodes1 = data
      this.visNodes = new vis.DataSet(this.nodes1)
      this.edges1 = []
      this.visEdges = new vis.DataSet(this.edges1)
    });
  }

  ngAfterViewInit() {

    var optionsFA = {
      height : 270 + 'px',
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
        organization: {
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

    // const nodes = new vis.DataSet([
    //   {id: 'a', label: 'Consortium 1', group : 'consortium'},
    //   {id: 'b', label: 'Org 1', group : 'org'},
    //   {id: 'c', label: 'Org 2', group : 'org'},
    //   {id: 'd', label: 'Peer 1', group : 'peer'},
    //   {id: 'e', label: 'Peer 2', group : 'peer'},
    //   {id: 'f', label: 'Peer 3', group : 'peer'},
    //   {id: 'g', label: 'Channel 1', group : 'channel'},
    //   {id: 'h', label: 'Consortium 1', group : 'consortium'},
    //   {id: 'h', label: 'Org 1', group : 'org'},
    //   {id: 'i', label: 'Org 2', group : 'org'},
    //   {id: 'j', label: 'Peer 4', group : 'peer'},
    //   {id: 'k', label: 'Peer 5', group : 'peer'},
    //   {id: 'l', label: 'Channel 2', group : 'channel'},
    //   {id: 'm', label: 'Peer 6', group : 'peer'}
    // ]);
  
    // const edges = new vis.DataSet([
    //   {from: 1, to: 2},
    //   {from: 1, to: 3},
    //   {from: 2, to: 4},
    //   {from: 2, to: 5},
    //   {from: 3, to: 6},
    //   {from: 3, to: 7},
    //   {from: 8, to: 9},
    //   {from: 8, to: 10},
    //   {from: 9, to: 11},
    //   {from: 9, to: 12},
    //   {from: 10, to: 13},
    //   {from: 10, to: 14}
    // ]);
  
    // const data = {
    //   nodes: nodes,
    //   edges: edges
    // };

    const data = {
      nodes: this.visNodes,
      edges: this.visEdges
    };
  
    this.network = new vis.Network(this.element.nativeElement, data, optionsFA); 

    this.network.on( 'click', (properties) => {
      var ids = properties.nodes;
      // var clickedNodes = nodes.get(ids);
      // console.log('clicked nodes:', clickedNodes);
      this.router.navigateByUrl('/chaincode');
  });

  }

}