import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { provideRouter } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MapPage } from './map.page';

@NgModule({
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
	],
	declarations: [
		MapPage,
	],
	providers: [
		provideRouter([
			{ path: '', component: MapPage },
		]),
	],
})
export class MapModule {
}
