import { Component } from '@angular/core';
import {FormControl} from '@angular/forms';

import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent {
  IMAGE_BASE_URL = '../assets/img/examples';

  myData = [
    {
      userIcon: `${this.IMAGE_BASE_URL}/icon_Yochan.png`,
      title: 'ペットを紹介するよ！',
      userName: 'Yochan',
      image: `${this.IMAGE_BASE_URL}/Yochan.jpeg`,
      description: `
        ネコ（猫）は、狭義には食肉目ネコ科ネコ属に分類される
        ヨーロッパヤマネコが家畜化されたイエネコ（家猫、Felis silvestris catus）に対する通称である。
        人間によくなつくため、イヌ（犬）と並ぶ代表的なペットとして世界中で広く飼われている。
      `
    },
    {
      userIcon: `${this.IMAGE_BASE_URL}/icon_Tasan.png`,
      title: 'ペットを紹介するよ！',
      userName: 'Tasan',
      image: `${this.IMAGE_BASE_URL}/Tasan.jpg`,
      description: `
            柴犬（しばいぬ）は、日本原産の日本犬の一種。
            オスは体高38 - 41cm、メスは35 - 38cmの犬種。
            日本の天然記念物に指定された7つの日本犬種（現存は6犬種）の1つで、
            指定は1936年（昭和11年）12月16日。日本における飼育頭数は最も多い。
      `
    },
    {
      userIcon: `${this.IMAGE_BASE_URL}/icon_Yochan.png`,
      userName: 'ChibiYochan',
      title: 'ペットを紹介するよ！',
      image: `${this.IMAGE_BASE_URL}/ChibiYochan.jpg`,
      description: `
        トラ（虎、Panthera tigris）は、食肉目ネコ科ヒョウ属に分類される食肉類。
      `
    }

  ];
  stateCtrl: FormControl;
  filteredStates: any;
  selectedValue: string;
  sliderValue: number;
  folders = [
    {name: '写真', updated: new Date('2017/01/20')},
    {name: '料理', updated: new Date('2016/10/2')},
    {name: '仕事', updated: new Date('2016/11/22')},
  ];

  notes = [
    {name: '夏休み', updated: new Date('2015/08/2')},
    {name: 'キッチンリフォーム', updated: new Date('2016/10/2')},
  ];


  tiles = [
    {text: 'その１', cols: 3, rows: 1,  color: 'lightblue'},
    {text: 'その2', cols: 1, rows: 2,  color: 'lightgreen'},
    {text: 'その3', cols: 1, rows: 1,  color: 'lightpink'},
    {text: 'その4', cols: 2, rows: 1,  color: '#DDBDF1'},
    {text: 'その5', cols: 2, rows: 2,  color: '#11BDF1'},
    {text: 'その6', cols: 2, rows: 1,  color: '#DD11F1'},
    {text: 'その7', cols: 2, rows: 1,  color: '#DFF1F1'},    

  ];

  foods = [
    {value: 'steak', viewValue: 'ステーキ'},
    {value: 'pizza', viewValue: 'ピザ'},
    {value: 'tacos', viewValue: 'タコス'},
    {value: 'sushi', viewValue: '寿司'},
  ];

  states = [
    'Alabama',
    'Alaska',
    'Arizona',
    'Arkansas',
    'California',
    'Colorado',
    'Connecticut',
    'Delaware',
    'Florida',
    'Georgia',
    'Hawaii',
    'Idaho',
    'Illinois',
    'Indiana',
    'Iowa',
    'Kansas',
    'Kentucky',
    'Louisiana',
    'Maine',
    'Maryland',
    'Massachusetts',
    'Michigan',
    'Minnesota',
    'Mississippi',
    'Missouri',
    'Montana',
    'Nebraska',
    'Nevada',
    'New Hampshire',
    'New Jersey',
    'New Mexico',
    'New York',
    'North Carolina',
    'North Dakota',
    'Ohio',
    'Oklahoma',
    'Oregon',
    'Pennsylvania',
    'Rhode Island',
    'South Carolina',
    'South Dakota',
    'Tennessee',
    'Texas',
    'Utah',
    'Vermont',
    'Virginia',
    'Washington',
    'West Virginia',
    'Wisconsin',
    'Wyoming',
  ];

  constructor(private http: Http){
    this.stateCtrl = new FormControl();

    this.filteredStates = this.stateCtrl.valueChanges
        .startWith(null)
        .map(name => this.filterStates(name));
  }

  filterStates(val: string) {
    return val ? this.states.filter(s => s.toLowerCase().indexOf(val.toLowerCase()) === 0)
               : this.states;
  }
}
