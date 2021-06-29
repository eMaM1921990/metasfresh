import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Loader from '../app/Loader';
import { getTargetIndicatorsDetails } from '../../actions/DashboardActions';
import moment from 'moment';

const ELAPSED_TIME_REFRESH_INTERVAL_MILLIS = 60000;

/**
 * @returns renders a string like "a minute ago" for a given timestamp
 */
function computeRenderedLastComputedString(computedTimestamp) {
  return moment(computedTimestamp).fromNow();
}

class Indicator extends Component {
  constructor(props) {
    super(props);
    this.state = { renderedLastComputedString: null };
  }

  /**
   * Updates the local computed timestamp with the one received from the props. Case when it comes from a query via xhr or via WS
   * @param {props} param0
   * @returns
   */
  static getDerivedStateFromProps({ data: { computedTimestamp } }) {
    return {
      renderedLastComputedString: computeRenderedLastComputedString(
        computedTimestamp
      ),
    };
  }

  componentDidMount() {
    this.interval = setInterval(
      () => this.updateRenderedLastComputedString(),
      ELAPSED_TIME_REFRESH_INTERVAL_MILLIS
    );
  }

  updateRenderedLastComputedString = () => {
    const { data } = this.props;
    const { computedTimestamp } = data;
    const renderedLastComputedString = computeRenderedLastComputedString(
      computedTimestamp
    );
    return this.setState({ renderedLastComputedString });
  };

  /**
   * @method showDetails
   * @summary Calls the getTargetIndicatorsDetails dashboard action to get the windowId and the viewId and
   *          once those are retrieved it will open the view in a new browser tab
   * @param {string} indicatorId
   */
  showDetails = (indicatorId) => {
    getTargetIndicatorsDetails(indicatorId).then((detailsResp) => {
      const { viewId, windowId } = detailsResp.data;
      let detailsTab = window.open(
        `${window.location.origin}/window/${windowId}?viewId=${viewId}`,
        '_blank'
      );
      detailsTab.focus();
    });
  };

  /**
   * @summary Clean up the interval used to prevent it from leaving errors and leaking memory
   */
  componentWillUnmount() {
    clearInterval(this.interval);
  }

  render() {
    const {
      id,
      amount,
      unit,
      caption,
      loader,
      fullWidth,
      editmode,
      framework,
      zoomToDetailsAvailable,
      data: { computedTimestamp },
    } = this.props;
    const { renderedLastComputedString } = this.state;

    if (loader)
      return (
        <div className="indicator">
          <Loader />
        </div>
      );

    return (
      <div
        className={
          'indicator js-indicator ' +
          (editmode || framework ? 'indicator-draggable ' : '')
        }
        style={fullWidth ? { width: '100%' } : {}}
      >
        <div>
          <div className="indicator-kpi-caption">{caption}</div>
          {zoomToDetailsAvailable && (
            <div
              className="indicator-details-link"
              onClick={() => this.showDetails(id)}
            >
              DETAILS
            </div>
          )}
        </div>
        <div className="indicator-data">
          <div className="indicator-amount">{amount}</div>
          <div className="indicator-unit">{unit}</div>
        </div>
        {renderedLastComputedString && (
          <div className="indicator-last-updated">
            <span
              className="indicator-fuzzy"
              href="#"
              data-toggle="tooltip"
              data-placement="top"
              title={moment(computedTimestamp).format('LLL z')}
            >
              <i className="meta-icon-reload" />
              {renderedLastComputedString}
            </span>
          </div>
        )}
      </div>
    );
  }
}

Indicator.propTypes = {
  id: PropTypes.number,
  value: PropTypes.any,
  caption: PropTypes.string,
  loader: PropTypes.any,
  fullWidth: PropTypes.bool,
  editmode: PropTypes.bool,
  framework: PropTypes.any,
  amount: PropTypes.number,
  unit: PropTypes.string,
  zoomToDetailsAvailable: PropTypes.bool,
  data: PropTypes.object,
};

export default Indicator;
