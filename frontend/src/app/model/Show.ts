import {FileInfo} from '@syncfusion/ej2-angular-inputs';

export interface Show {
  id: number;
  name: string;
  description: string;

  // TODO: To implement in the next Sprint

  /*
  coverImageRawData: string | Blob;
  coverImageExtension: string;
  */
  photoUrl: string;
  productionLocation: string;
  language: string;
  actors: any[] | string;
  directors: any[] | string;
  comingSoon: boolean;
  categories: any[] | string;
  highlighted: boolean;
  length: number | string;
  releaseDate: string;
  urlHighlighted: string;
  urlTrailer: string;
  rating: string;
}
