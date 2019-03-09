import { Component, OnInit } from '@angular/core';

declare const $: any;
declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/dashboard', title: 'Dashboard',  icon: 'dashboard', class: 'material-icons' },
    { path: '/consortium', title: 'Consortium',  icon: '', class: 'fa fa-connectdevelop' },
    { path: '/orgs', title: 'Organisations',  icon:'', class: 'fa fa-first-order' },
    { path: '/peers', title: 'Peers',  icon:'device_hub', class: 'material-icons' },
    { path: '/channels', title: 'Channels',  icon:'', class: 'fa fa-podcast' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor() { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
      if ($(window).width() > 991) {
          return false;
      }
      return true;
  };
}
