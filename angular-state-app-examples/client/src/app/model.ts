import { Backend } from './backend';
import { WatchService } from './watch';
import { Store, Reducer } from './store';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';


export type Talk = {
  id: number;
  title: string;
  speaker: string;
  description: string;
  yourRating: number;
  rating: number;
};

export type Filters = {
  speaker: string;
  title: string;
  minRating: number;
};

export type ShowDetail = {
  type: 'SHOW_DETAIL',
  talkId: number;
};

export type Filter = {
  type: 'FILTER',
  filters: Filters;
};

export type Watch = {
  type: 'WATCH',
  talkId: number;
};

export type Rate = {
  type: 'RATE',
  talkId: number;
  rating: number;
};

export type Unrate = {
  type: 'UNRATE',
  talkId: number;
  error: any;
};

export type Action = Filter | ShowDetail | Watch | Rate | Unrate;


export interface State {
  talks: { [id: number]: Talk};
  list: number[];
  filters: Filters;
  watched: { [id: number]: boolean};
}

export const initState: State = {
  talks: {},
  list: [],
  filters: {
    speaker: null,
    title: null,
    minRating: 0
  },
  watched: {}
};

export function reducer(backend: Backend, watch: WatchService): Reducer<State, Action> {
  return (store: Store<State, Action>, state: State, action: Action): State | Observable<State> => {
    switch (action.type) {
      case 'FILTER':
        return backend.findTalks(action.filters).pipe(map(r => ({...state, ...r, filters: action.filters})));
      case 'SHOW_DETAIL':
        const talkToWatch = state.talks[action.talkId];
        watch.watch(talkToWatch);
        const updatedWatched = {...state.watched, [action.talkId]: true };
        return {...state, watched: updatedWatched};
      case 'RATE':
        backend.rateTalk(action.talkId, action.rating).pipe(
          catchError(e => store.sendAction({type: 'UNRATE', talkId: action.talkId, error: e}))
        ).forEach(() => {});

        const talkToRate = state.talks[action.talkId];
        const ratedTalk = {...talkToRate, yourRating: action.rating};
        const updatedTalks = {...state.talks, [action.talkId]: ratedTalk };
        return {...state, talks: updatedTalks };
      case 'UNRATE':
        const talkToUnrate = state.talks[action.talkId];
        const unratedTalk = {...talkToUnrate, yourRating: null };
        const updatedTalksAfterUnrating = {...state.talks, [action.talkId]: unratedTalk };
        return {...state, talks: updatedTalksAfterUnrating };
      default:
        // 現行のstateを返却
        return state;
    }
  };
}

