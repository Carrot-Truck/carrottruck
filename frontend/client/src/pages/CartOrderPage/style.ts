import { styled } from "styled-components";

export const CartOrderPageLayout = styled.div`
  width: 360px; /* Set the width of the container */
  padding-bottom: 200px; /* Add padding to the bottom */
  color: black; /* Set text color */
  margin: auto; /* Center the layout in the middle of the page */
  background-color: #ffffff; /* Set background color to white */

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px;
    background-color: #f7f7f7; /* Light grey background for the header */
    border-bottom: 1px solid #eaeaea; /* Add a border to the bottom of the header */

    p {
      font-weight: bold;
      font-size: 1.5rem; /* Larger text for the page title */
    }
  }

  /* Style for each section */
  div {
    padding: 16px;
    border-bottom: 1px solid #eaeaea; /* Add a border to each section */

    p {
      margin: 0;
      padding: 8px 0; /* Add padding to top and bottom of text */
      font-size: 1rem;
    }
  }

  /* Style for the button */
  Button {
    width: calc(100% - 32px); /* Full width minus padding */
    margin: 16px;
    background-color: #ff6b6b; /* Orange background for the button */
    color: white;
    border: none;
    padding: 12px 0; /* Padding inside the button */
    border-radius: 8px; /* Rounded corners for the button */
    font-weight: bold;
    cursor: pointer; /* Pointer cursor on hover */

    &:hover {
      background-color: #e65a5a; /* Darker orange on hover */
    }
  }
`;