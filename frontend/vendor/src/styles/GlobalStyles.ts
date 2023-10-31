import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

export const GlobalStyles = createGlobalStyle`
    ${reset};
    
    :root {
        /* color */
        --main-color : #FFA126;
        --sub-color-1 : #FFD9A8;
        --success-color : #00A3FF;
        --danger-color : #ff2449;
        --white-color : #ffffff;

        --gray-500 : #494949;
        --gray-400 : #333D47;
        --gray-300 : #7B858F;
        --gray-200 : #B9C0C7;
        --gray-100 : #E8EBED;

        --black-500 : #000000;
        --black-400 : #292D32;
        --black-300 : #292D32;
        --black-200 : #2A2A2A;
        --black-100 : #333333;
        --footer-bg-color : #eceff3;

        /* radius */
        --radius-s : 5px;
        --radius-m : 14px;
        --radius-l : 99px;

        /* width */
        --content-width-xs : 250px;
        --content-width-s : 512px;
        --content-width-m : 756px;
        --content-width-l : 1024px;
        --content-width-full : 100%;
    }

    body{
        font-family: 'Pretendard';
        padding: 0;
        margin: 0;
        overflow-y: scroll;
    };

    // 스크롤 바
     &::-webkit-scrollbar {
    } 

    a{
        text-decoration: none;
        color: inherit;
    }
    *{
        box-sizing: border-box;
    }
    html, body, div, span, h1, h2, h3, h4, h5, h6, p, 
    a, dl, dt, dd, ol, ul, li, form, label, table{
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 16px;
        vertical-align: baseline;
    }

    ol, ul{
        list-style: none;
    }
    button {
        border: 0;
        background: transparent;
        cursor: pointer;
    }
`;
