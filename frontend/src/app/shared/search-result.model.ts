import {Person} from "../person/person.model";
import {Bucket} from "./bucket.model";

export class SearchResult {
  persons: Person[];
  countries: Bucket[];
  years: Bucket[];
}
