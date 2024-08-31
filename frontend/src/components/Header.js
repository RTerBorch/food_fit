export default function Header(props) {
  return (
    <header className="App-header">
      <img className="App-logo" src={props.logoSrc} alt="logo" />
      <h1 className="App-title">{props.pageTitle}</h1>
    </header>
  );
}
