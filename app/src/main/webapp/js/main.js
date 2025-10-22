const form = document.getElementById("form")
const xInputs = document.querySelectorAll('input[name="x-input"]');
const yInput = document.getElementById("y")
const rInput = document.getElementById("r")

const xErrorText = document.getElementById("x-error")
const yErrorText = document.getElementById("y-error")
const rErrorText = document.getElementById("r-error")

const svg = document.getElementById('svg-graph');

const xValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2]
let xValue = -2

xInputs.forEach(checkbox => {
  checkbox.addEventListener("click", () => checkInputs(checkbox))
})

rInput.addEventListener("change", () => {
  const scaledCoords = scaleByRadius(xValue, parseFloat(yInput.value))
  const x = findClosestPoint(scaledCoords.x)
  xInputs.forEach(input => {
    if (input.value === x.toString()) {
      input.checked = true
      checkInputs(input)
    }
  })
  xValue = x
  yInput.value = scaledCoords.y
})

svg.addEventListener('click', function(e) {

  const pt = svg.createSVGPoint();

  pt.x = e.clientX;
  pt.y = e.clientY;

  const svgP = pt.matrixTransform(svg.getScreenCTM().inverse());
  const scaledCoords = scaleByRadius(svgP.x - 150, 150 - svgP.y)
  const x = findClosestPoint(scaledCoords.x)

  xInputs.forEach(input => {
    if (input.value === x.toString()) {
      input.checked = true
      checkInputs(input)
    }
  })
  xValue = x
  yInput.value = scaledCoords.y
});

form.addEventListener("submit", () => {

  const x = xValue
  const y = yInput.value
  const r = rInput.value

  const errorText = validate(x, y, r)

  switch (errorText.input) {
    case "x":
      xErrorText.innerHTML = errorText.text
      break
    case "y":
      yErrorText.innerHTML = errorText.text
      break
    case "r":
      rErrorText.innerHTML = errorText.text
      break
    default:
      window.open(`/main?x=${x}&y=${y}&r=${r}`)
  }
})

const checkInputs = (checkbox) => {
  if (checkbox.checked) {
    xValue = checkbox.value
    xInputs.forEach((cb) => {
      if (cb !== checkbox) {
        cb.checked = false;
      }
    })
  }
}

const scaleByRadius = (x, y) => {
  return {
    x: (x * rInput.value / 120).toFixed(1),
    y: (y * rInput.value / 120).toFixed(1)
  }
}

const findClosestPoint = (x) => {
  let closestPoint = 0;
  let minDiff = Infinity;

  xValues.forEach(value => {
    const diff = Math.abs(value - parseFloat(x));
    if (diff < minDiff) {
      minDiff = diff;
      closestPoint = value;
    }
  });
  return closestPoint;
}

const addCircle = (x, y) => {
  const pt = svg.createSVGPoint();

  pt.x = x
  pt.y = y

  const svgP = pt.matrixTransform(svg.getScreenCTM().inverse());

  const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
  circle.setAttribute("cx", svgP.x.toString());
  circle.setAttribute("cy", svgP.y.toString());
  circle.setAttribute("r", (rInput.value * 0.25).toString());
  circle.setAttribute("fill", "red");

  svg.appendChild(circle);
}

const validate = (x, y, r) => {
  if (!Number.isFinite(Number(r)))
    return {
      input: "x",
      text: "Параметр х не является числом"
    }
  if (!Number.isFinite(Number(r)))
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