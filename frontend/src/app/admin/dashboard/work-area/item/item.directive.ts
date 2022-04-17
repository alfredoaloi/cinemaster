import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  // tslint:disable-next-line:directive-selector
  selector: '[itemHost]',
})

export class ItemDirective {
  constructor(public viewContainerRef: ViewContainerRef) { }
}
