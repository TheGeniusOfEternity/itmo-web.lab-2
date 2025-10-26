const form = document.getElementById("form")
const xInputs = document.querySelectorAll('input[name="x-input"]');
const yInput = document.getElementById("y")
const rInput = document.getElementById("r")

const xErrorText = document.getElementById("x-error")
const yErrorText = document.getElementById("y-error")
const rErrorText = document.getElementById("r-error")


const svg = document.getElementById('svg-graph');
const hits = localStorage.getItem("hits")

let xValue = -2
let yPrevValue = ""

if (hits === null) svg.classList.add("rendered")

window.onload = () => {
  if (hits !== null) {
    const data = JSON.parse(hits)
    svg.classList.remove("rendered")
    data.forEach((row, index) => {
      const x = parseFloat(row.cells[0]);
      const y = parseFloat(row.cells[1]);
      const r = parseFloat(row.cells[2]);

      const svgX = (x * 120 / r) + 150;
      const svgY = 150 - (y * 120 / r);

      const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
      circle.setAttribute("cx", svgX.toString());
      circle.setAttribute("cy", svgY.toString());
      circle.setAttribute("r", "2");
      circle.setAttribute(
        "fill",
        `${data.length - index === 1 ? 'purple' : 'black'}`
      );

      svg.appendChild(circle);
    })
    localStorage.removeItem("hits")
  }
  svg.classList.add("rendered")
}

xInputs.forEach(checkbox => {
  checkbox.addEventListener("click", () => {
    if (checkbox.checked) {
      xValue = checkbox.value
      xInputs.forEach((cb) => {
        if (cb !== checkbox) {
          cb.checked = false;
        }
      })
    }
  })
})

yInput.addEventListener("input", () => {
  const regex = /^(-|-?\d|-?\d\.\d{0,2}|)$/
  if (!regex.test(yInput.value) && yInput.value !== "" || yInput.value === "-0")
    yInput.value = yPrevValue
  const num = parseFloat(yInput.value)
  if ((isNaN(num) || -3 >= num || num >= 3) && !yInput.value.match(/^-?$/))
    yInput.value = yPrevValue
    yPrevValue = yInput.value
})

svg.addEventListener('click', (e) => {

  const pt = svg.createSVGPoint();

  pt.x = e.clientX;
  pt.y = e.clientY;

  const svgP = pt.matrixTransform(svg.getScreenCTM().inverse());

  xValue = ((svgP.x  - 150) * rInput.value / 120).toFixed(2)
  yInput.value = ((150 - svgP.y) * rInput.value / 120).toFixed(2)

  sendRequest()
});

form.addEventListener("submit", (e) => {
  e.preventDefault()
  sendRequest()
})

const sendRequest = () => {

  const x = xValue
  const y = yInput.value
  const r = rInput.value

  const errorText = validate(x, y, r)

  switch (errorText.input) {
    case "x":
      xErrorText.innerHTML = errorText.text
      xErrorText.classList.add("show")
      break
    case "y":
      yErrorText.innerHTML = errorText.text
      yErrorText.classList.add("show")
      break
    case "r":
      rErrorText.innerHTML = errorText.text
      rErrorText.classList.add("show")
      break
    default:
      const params = new URLSearchParams({
        x: x.toString(),
        y: y.toString(),
        r: r.toString(),
      })
      location.assign(`/main?${params}`)
  }
}

const validate = (x, y, r) => {
  if (!Number.isFinite(Number(x)))
    return {
      input: "x",
      text: "Параметр х не является числом"
    }
  if (!Number.isFinite(Number(y)))
    return {
      input: "y",
      text: "Параметр y не является числом"
    }
  if (!Number.isFinite(Number(r)))
    return {
      input: "r",
      text: "Параметр r не является числом"
    }
  return {
    input: "",
    text: ""
  }
}