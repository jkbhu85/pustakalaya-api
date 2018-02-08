(function IIFE(document){
  let navHtml = 
`<ul>
  <li>
    <a href="index.html">Home</a>
  </li>
</ul>
`;

  let navElm = document.getElementsByTagName("nav")[0];
  
  if (navElm) {
    navElm.innerHTML = navHtml;
  }
})(document);