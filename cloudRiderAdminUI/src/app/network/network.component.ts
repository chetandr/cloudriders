import { Component, ElementRef, ViewChild, AfterViewInit} from '@angular/core';

import * as vis from 'vis';

type Icon = Array<{code: string, color: string}>;

@Component({
  selector: 'network-component',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})

export class NetworkComponent implements AfterViewInit {
  @ViewChild('vis') element: ElementRef;
  network: vis.Network;

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

  constructor() { }

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

    const nodes = new vis.DataSet([
      {id: 1, label: 'Consortium 1', group : 'consortium'},
      {id: 2, label: 'Org 1', group : 'org'},
      {id: 3, label: 'Org 2', group : 'org'},
      {id: 4, label: 'Peer 1', group : 'peer'},
      {id: 5, label: 'Peer 2', group : 'peer'},
      {id: 6, label: 'Peer 3', group : 'peer'},
      {id: 7, label: 'Channel 1', group : 'channel'},
      {id: 8, label: 'Consortium 1', group : 'consortium'},
      {id: 9, label: 'Org 1', group : 'org'},
      {id: 10, label: 'Org 2', group : 'org'},
      {id: 11, label: 'Peer 4', group : 'peer'},
      {id: 12, label: 'Peer 5', group : 'peer'},
      {id: 13, label: 'Channel 2', group : 'channel'},
      {id: 14, label: 'Peer 6', group : 'peer'}
    ]);
  
    const edges = new vis.DataSet([
      {from: 1, to: 2},
      {from: 1, to: 3},
      {from: 2, to: 4},
      {from: 2, to: 5},
      {from: 3, to: 6},
      {from: 3, to: 7},
      {from: 8, to: 9},
      {from: 8, to: 10},
      {from: 9, to: 11},
      {from: 9, to: 12},
      {from: 10, to: 13},
      {from: 10, to: 14}
    ]);
  
    const data = {
      nodes: nodes,
      edges: edges
    };
  
    this.network = new vis.Network(this.element.nativeElement, data, optionsFA); 

    setTimeout(() => {
      console.log("shweta");
      nodes.add({id: 15, label: 'Peer 10', group : 'peer'});
      edges.add({from: 15, to: 9});
    }, 1000);

    this.network.on( 'click', function(properties) {
      var ids = properties.nodes;
      var clickedNodes = nodes.get(ids);
      console.log('clicked nodes:', clickedNodes);
  });

  }

}