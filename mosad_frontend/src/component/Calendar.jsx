import * as React from 'react';
import dayjs from 'dayjs';
import Badge from '@mui/material/Badge';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { PickersDay } from '@mui/x-date-pickers/PickersDay';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';
import { DayCalendarSkeleton } from '@mui/x-date-pickers/DayCalendarSkeleton';
import StarIcon from '@mui/icons-material/Star';

function ServerDay(props) {
  const { highlightedDays = [], day, outsideCurrentMonth, ...other } = props;

  const isHighlighted =
    !outsideCurrentMonth && highlightedDays.some((d) => day.isSame(d, 'day'));

  return (
    <Badge
      key={day.toString()}
      overlap="circular"
      badgeContent={isHighlighted ? <StarIcon sx={{fontSize:'small'}}/> : undefined}
    >
      <PickersDay {...other} outsideCurrentMonth={outsideCurrentMonth} day={day} />
    </Badge>
  );
}

export default function Calendar({ highlightedDates = [] }) {
  const [isLoading, setIsLoading] = React.useState(false);
  const [currentHighlightedDays, setCurrentHighlightedDays] = React.useState(
    highlightedDates.map((date) => dayjs(date))
  );

  React.useEffect(() => {
    setCurrentHighlightedDays(highlightedDates.map((date) => dayjs(date)));
  }, [highlightedDates]);

  const handleMonthChange = (date) => {
    setIsLoading(true);
    setIsLoading(false);
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DateCalendar
        loading={isLoading}
        onMonthChange={handleMonthChange}
        renderLoading={() => <DayCalendarSkeleton />}
        slots={{
          day: ServerDay,
        }}
        slotProps={{
          day: {
            highlightedDays: currentHighlightedDays,
          },
        }}
      />
    </LocalizationProvider>
  );
}