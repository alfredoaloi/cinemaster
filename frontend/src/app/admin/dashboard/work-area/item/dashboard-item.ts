import {Type} from '@angular/core';

export class DashboardItem {
  constructor(public component: Type<any>, public type: any, public info: any) {}
}
