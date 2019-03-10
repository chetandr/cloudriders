import { Component, ElementRef, ViewChild, AfterViewInit, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as vis from 'vis';

type Icon = Array<{code: string, color: string}>;

@Component({
  selector: 'network-component',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})

export class NetworkComponent implements AfterViewInit, OnInit {
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

  constructor(private http: HttpClient) { }

  ngOnInit() {
      
  }

  ngAfterViewInit() {

    let nodes1 = []

    let edges1 = []

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

    this.http.get("http://127.0.0.1:3000/hyperverse/getnodes").subscribe((data : any[]) => {

      this.http.get("http://127.0.0.1:3000/hyperverse/getnetworkgraph").subscribe((edges : any[]) => {
        data.forEach(element => {
          console.log(element)
          let obj = {id: element.id, label: element.label, group : element.group}
          nodes1.push(obj)
        });

        edges.forEach(element => {
          console.log(element)
          let from = data.filter((node) => {
            if (node.label == element.from) {
                return node.id
            }
          });
          console.log("....from"+from[0].id)
          let to = data.filter((node) => {
            if (node.label == element.to) {
                return node.id
            }
          });
          console.log("....from"+to[0].id)
          let obj = {from: from[0].id, to: to[0].id}
          edges1.push(obj)
        });
  
        let visnodes = new vis.DataSet({});
        visnodes.add(nodes1)
  
        let visedges = new vis.DataSet({})
        visedges.add(edges1)
  
        const data1 = {
          nodes: visnodes,
          edges: visedges
        };
  
        this.network = new vis.Network(this.element.nativeElement, data1, optionsFA);
      })
    })

  }

}
