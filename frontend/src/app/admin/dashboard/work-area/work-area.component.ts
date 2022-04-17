import {Component, Input, OnInit, ViewChild, ComponentFactoryResolver, OnDestroy} from '@angular/core';
import {ItemDirective} from './item/item.directive';
import {DashboardItem} from './item/dashboard-item';

@Component({
  selector: 'app-work-area',
  templateUrl: './work-area.component.html',
  styleUrls: ['./work-area.component.css']
})
export class WorkAreaComponent implements OnInit {
  @Input() item: DashboardItem;
  @ViewChild(ItemDirective, {static: true}) itemHost: ItemDirective;

  constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

  ngOnInit(): void {
    this.loadComponent(this.item);
  }

  loadComponent(item: DashboardItem): void {
    this.item = item;
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.item.component);

    const viewContainerRef = this.itemHost.viewContainerRef;
    viewContainerRef.clear();

    const componentRef = viewContainerRef.createComponent<DashboardItem>(componentFactory);
    componentRef.instance.type = this.item.type;
    componentRef.instance.info = this.item.info;
  }
}
